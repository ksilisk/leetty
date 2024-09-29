package com.ksilisk.leetty.web.service.exception;

public class LeettyWebServiceException extends RuntimeException {
    public LeettyWebServiceException(String message) {
        super(message);
    }

    public LeettyWebServiceException() {
        super("Internal Server Error");
    }
}
