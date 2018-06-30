package com.rockyou.adhawk.interview.webframework;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public abstract class Responder implements HttpHandler {

    public final String requestPath;

    public Responder(String requestPath) {
        this.requestPath = requestPath;
    }

    public abstract HttpResponse respond(Map<String, String> parameters);

    @SuppressWarnings("restriction")
	@Override
    public void handle(@SuppressWarnings("restriction") HttpExchange httpExchange) throws IOException {

        @SuppressWarnings("restriction")
		URI requestURI = httpExchange.getRequestURI();

        String path = requestURI.getPath();
        String query = requestURI.getQuery();

        HashMap<String, String> parameters = new HashMap<>();

        Arrays.stream(query.split("&")).forEach((element) -> {
            String[] keyVal = element.split("=");
            if (keyVal.length == 2) {
                parameters.put(keyVal[0], keyVal[1]);
            }
        });

        HttpResponse childResponse = this.respond(parameters);
        httpExchange.sendResponseHeaders(childResponse.status.asInt(), childResponse.body.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(childResponse.body);
        outputStream.close();
    }
}
