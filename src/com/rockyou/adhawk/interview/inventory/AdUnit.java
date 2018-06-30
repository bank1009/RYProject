package com.rockyou.adhawk.interview.inventory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;

public class AdUnit implements Comparable<AdUnit> {

    private final int id;
    private final BigDecimal bidPrice;
    private final String clickUrl;
    private final String creativeUrl;
    private final Collection<String> placements;

    public AdUnit(int id, BigDecimal bidPrice, String clickUrl, String creativeUrl, Collection<String> placements) {
        this.id = id;
        this.bidPrice = bidPrice;
        this.clickUrl = clickUrl;
        this.creativeUrl = creativeUrl;
        this.placements = placements;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public String getCreativeUrl() {
        return creativeUrl;
    }

    public Collection<String> getPlacements() {
        return placements;
    }

    @Override
    public int compareTo(AdUnit bidPrice) {
        // descending order
        return bidPrice.getBidPrice().subtract(this.bidPrice).intValue();
    }

    public static Comparator<AdUnit> BidComparator = new Comparator<AdUnit>() {

        public int compare(BigDecimal fruit1, BigDecimal fruit2) {


            // descending order
            return fruit2.compareTo(fruit1);
        }

        @Override
        public int compare(AdUnit o1, AdUnit o2) {
            // TODO Auto-generated method stub
            return o2.getBidPrice().compareTo(o1.getBidPrice());
        }

    };

}
