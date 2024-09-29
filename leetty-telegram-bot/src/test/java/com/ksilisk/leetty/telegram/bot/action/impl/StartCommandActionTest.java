package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.START_COMMAND;

class StartCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        StartCommandAction startCommandAction = new StartCommandAction(null, null, null, null);
        // when
        LeettyBotEvent event = startCommandAction.getEvent();
        // then
        Assertions.assertEquals(START_COMMAND, event);
    }
}