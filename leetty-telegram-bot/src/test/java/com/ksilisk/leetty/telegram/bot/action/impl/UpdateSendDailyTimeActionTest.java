package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

class UpdateSendDailyTimeActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        UpdateSendDailyTimeAction updateSendDailyTimeAction = new UpdateSendDailyTimeAction(null);
        // when
        LeettyBotEvent event = updateSendDailyTimeAction.getEvent();
        // then
        Assertions.assertEquals(UPDATE_SEND_DAILY_TIME, event);
    }
}