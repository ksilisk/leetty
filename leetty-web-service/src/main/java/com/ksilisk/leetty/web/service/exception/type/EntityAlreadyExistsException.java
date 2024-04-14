package com.ksilisk.leetty.web.service.exception.type;

import com.ksilisk.leetty.web.service.exception.LeettyWebServiceException;

public class EntityAlreadyExistsException extends LeettyWebServiceException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public static class ChatAlreadyExistsException extends EntityAlreadyExistsException {
        public ChatAlreadyExistsException() {
            super("Chat already exists");
        }
    }

    public static class UserAlreadyExistsException extends EntityAlreadyExistsException {

        public UserAlreadyExistsException() {
            super("User already exists");
        }
    }
}
