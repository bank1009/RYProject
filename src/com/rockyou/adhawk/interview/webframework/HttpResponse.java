package com.rockyou.adhawk.interview.webframework;

public class HttpResponse {
    public HttpStatus status;

    public byte[] body;

    public HttpResponse(HttpStatus status, byte[] body) {
        this.status = status;
        this.body = body;
    }

    public HttpResponse(HttpStatus status, String body) {
        this(status, body.getBytes());
    }

    /**
     * This constructor is not preferred, either of the other two are recommended.
     */
    public HttpResponse() {
        this.status = HttpStatus.Ok;
        this.body = new byte[] {};
    }
}
