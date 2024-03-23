package com.ksilisk.leetty.telegram.bot.sender.impl;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import org.springframework.stereotype.Component;

@Component
public class LeettyUpdateSender extends Sender {
    private static final LeettyProperties.Bot BOT = LeettyProperties.Bot.LEETTY;

    protected LeettyUpdateSender(LeettyProperties leettyProperties) {
        super(leettyProperties.getBotProperties().get(BOT));
    }

    @Override
    public LeettyProperties.Bot getBot() {
        return BOT;
    }
}
