package com.ksilisk.leetty.web.service.exception.type;

import com.ksilisk.leetty.web.service.exception.LeettyWebServiceException;

public class EntityNotFoundException extends LeettyWebServiceException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static class ChatNotFoundException extends EntityNotFoundException {
        public ChatNotFoundException() {
            super("Chat not found");
        }
    }

    public static class UserNotFountException extends EntityNotFoundException {
        public UserNotFountException() {
            super("User not found");
        }
    }
}
