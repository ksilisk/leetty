package com.ksilisk.leetty.web.service.exception;

public class EntityNotFoundException extends RuntimeException {
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
