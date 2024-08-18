package com.ksilisk.leetty.telegram.bot.exception.type;

import com.ksilisk.leetty.telegram.bot.exception.LeettyTelegramBotException;

public class LeetCodeValidationUrlException extends LeettyTelegramBotException {
    public LeetCodeValidationUrlException(String message) {
        super(message);
    }

    public LeetCodeValidationUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
