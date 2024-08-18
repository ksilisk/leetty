package com.ksilisk.leetty.telegram.bot.exception;

public class LeettyTelegramBotException extends RuntimeException {
    public LeettyTelegramBotException(String message) {
        super(message);
    }

    public LeettyTelegramBotException(String message, Throwable cause) {
        super(message, cause);
    }
}
