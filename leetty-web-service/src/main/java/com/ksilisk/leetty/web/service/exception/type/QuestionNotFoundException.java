package com.ksilisk.leetty.web.service.exception.type;

import com.ksilisk.leetty.web.service.exception.LeettyWebServiceException;

public class QuestionNotFoundException extends LeettyWebServiceException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
