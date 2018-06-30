package com.rockyou.adhawk.interview.webframework;

public enum HttpStatus {
    Ok(200),
    NotFound(404);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int asInt() {
        return statusCode;
    }
}
