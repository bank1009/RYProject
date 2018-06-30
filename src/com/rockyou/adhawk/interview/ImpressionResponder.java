package com.rockyou.adhawk.interview;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.rockyou.adhawk.interview.inventory.AdUnit;
import com.rockyou.adhawk.interview.webframework.HttpResponse;
import com.rockyou.adhawk.interview.webframework.HttpStatus;
import com.rockyou.adhawk.interview.webframework.Responder;

public class ImpressionResponder extends Responder {

    private Collection<AdUnit> adUnits;
    

    public ImpressionResponder(String requestPath, Collection<AdUnit> ads) {
        super(requestPath);

        this.adUnits = ads;
    }

    @Override
    public HttpResponse respond(Map<String, String> parameters) {
    	//Create singleton object for keeping list of the requested user in 30 sec
    	UserIDSingleton userId = UserIDSingleton.getInstance();

        // TODO: implement this responder as defined in the README spec.
    	if(parameters.get("user_id") != null) {
    		String user_id = parameters.get("user_id");
    		if( userId.id.contains(user_id)) {
    			return new HttpResponse(HttpStatus.NotFound, "NOT FOUND (404)");
    		}
    	}
    	
        // Step 1 (Impression Requests)
        String placement = parameters.get("placement");
        System.out.println("placement=" + placement);
        List<AdUnit> adUnitList = new ArrayList<>();

        for (AdUnit adUnit : adUnits) {
            if (adUnit.getPlacements().toArray()[0].equals(placement)) {
                adUnitList.add(adUnit);
                System.out.println("Match id=" + adUnit.getId());
            }
        }

        // If no matching ad units
        if (adUnitList.size() == 0) {
            return new HttpResponse(HttpStatus.NotFound, "NOT FOUND (404)");
        }
        // Sort bid highest is first.
        Collections.sort(adUnitList, AdUnit.BidComparator);

        AdUnit winerUnit = adUnitList.get(0);
        BigDecimal pricePaid = new BigDecimal(0);
        if (adUnitList.size() > 1) {
            // GET the price paid is the second-highest bid

            for (AdUnit adUnit : adUnitList) {
                if (winerUnit.getBidPrice().compareTo(adUnit.getBidPrice()) != 0) {
                    pricePaid = adUnit.getBidPrice();
                    break;
                }
            }
            if (pricePaid.compareTo(new BigDecimal(0)) == 0) {
                pricePaid = adUnitList.get(0).getBidPrice();
            }
        } else {
            // if there is no second bidder, the bid price is $0.01
            BigDecimal lowPrice = new BigDecimal(0.10);
            pricePaid = lowPrice.setScale(2, RoundingMode.DOWN);
        }

        // Step 2 (Macro Replacement)
        String newURL = null;
        if (winerUnit.getClickUrl().indexOf("${AUCTION_PRICE}") > 0) {
            newURL = winerUnit.getClickUrl();
            try {
                newURL = newURL.replaceAll("\\$\\{AUCTION_PRICE\\}", pricePaid.toEngineeringString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        /*** Create JSON ***/
        JSONObject object = new JSONObject();
        try {
            object.put("id", winerUnit.getId());
            object.put("bid", pricePaid.toEngineeringString());
            object.put("creative", winerUnit.getCreativeUrl());
            if (newURL != null) {
                object.put("click_url", newURL);
            } else {
                object.put("click_url", winerUnit.getClickUrl());
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Keep the requested user id in the waiting list 
        userId.id.add(String.valueOf(winerUnit.getId()));
        Thread t = new Thread() {
        	 public void run(){
        	      	try {
        	      		//Wating for 30 sec ,then removing user id from the user list
        	      		System.out.println("----  before 30 sec");
        	   			userId.id.forEach(s -> System.out.println("-- " + s.toString()));
        	   			Thread.sleep(30000);
        	   			System.out.println("----  after 30 sec");
        	   			userId.id.remove(String.valueOf(winerUnit.getId()));
        	   			userId.id.forEach(s -> System.out.println("-- " +  s.toString()));
        	   			System.out.println("tread die after 30 sec");
        	   		} catch (InterruptedException e) {
        	   			// TODO Auto-generated catch block
        	   			e.printStackTrace();
        	   		}
        	         System.out.println("MyThread running");
        	      }
        };
        
        t.start();

        HttpResponse res = new HttpResponse(HttpStatus.Ok, object.toString());
        return res;
    }
    
    public static class UserIDSingleton implements Serializable{
    	private static final long serialVersionUID = 7526471155622776147L;
    	public List<String> id = new ArrayList<String>();
    	
    	private static class UserIDSingletonHelper {
    		private static final UserIDSingleton instance = new UserIDSingleton();
    		
    	}
    	
    	public static UserIDSingleton getInstance() {
    		return UserIDSingletonHelper.instance;
    	}
    }
   
}



