package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SET_SEND_DAILY_TIME;

class SetSendDailyTimeActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        SetSendDailyTimeAction setSendDailyTimeAction = new SetSendDailyTimeAction(null, null);
        // when
        LeettyBotEvent event = setSendDailyTimeAction.getEvent();
        // then
        Assertions.assertEquals(SET_SEND_DAILY_TIME, event);
    }
}