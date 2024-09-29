package com.ksilisk.leetty.web.service.exception.type;

import com.ksilisk.leetty.web.service.exception.LeettyWebServiceException;

public class LeettyRequestValidationException extends LeettyWebServiceException {
    public LeettyRequestValidationException() {
        super("Invalid request");
    }

    public LeettyRequestValidationException(String message) {
        super(message);
    }
}
