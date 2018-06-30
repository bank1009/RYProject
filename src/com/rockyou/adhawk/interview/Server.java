package com.rockyou.adhawk.interview;

import java.net.InetSocketAddress;

import com.rockyou.adhawk.interview.inventory.AdCache;
import com.rockyou.adhawk.interview.webframework.Responder;
import com.sun.net.httpserver.HttpServer;

public class Server {

	private final static int LISTEN_PORT = 8000;
	

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(LISTEN_PORT), 0);

		registerEndpoints(server);

		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("serve start");
	}

	@SuppressWarnings("restriction")
	private static void registerEndpoints(HttpServer server) {

		AdCache adCache = new AdCache();
		adCache.initialize();

		// Create the responder for serving impressions
		Responder impressionEndpoint = new ImpressionResponder("/impr", adCache.ads);
		server.createContext(impressionEndpoint.requestPath, impressionEndpoint);
	}
}



