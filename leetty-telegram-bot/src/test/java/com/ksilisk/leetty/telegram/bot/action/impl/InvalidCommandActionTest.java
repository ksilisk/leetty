package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INVALID;

class InvalidCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        InvalidCommandAction invalidCommandAction = new InvalidCommandAction(null, null);
        // when
        LeettyBotEvent event = invalidCommandAction.getEvent();
        // then
        Assertions.assertEquals(INVALID, event);
    }
}