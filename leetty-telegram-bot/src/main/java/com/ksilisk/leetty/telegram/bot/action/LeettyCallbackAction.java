package com.ksilisk.leetty.telegram.bot.action;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.action.CallbackAction;

public interface LeettyCallbackAction extends CallbackAction<LeettyBotStates, LeettyBotEvent, CallbackData> {
}
