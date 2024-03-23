package com.ksilisk.leetty.telegram.bot.action.leetty;

import com.ksilisk.leetty.telegram.bot.action.CallbackAction;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;

public interface LeettyCallbackAction extends CallbackAction<LeettyBotStates, LeettyBotEvent> {
    @Override
    default LeettyProperties.Bot getBot() {
        return LeettyProperties.Bot.LEETTY;
    }
}
