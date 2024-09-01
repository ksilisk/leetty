package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.INLINE_QUERY_COMMAND;

class InlineQueryCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        InlineQueryCommandAction inlineQueryCommandAction =
                new InlineQueryCommandAction(null, null, null, null, null);
        // when
        LeettyBotEvent event = inlineQueryCommandAction.getEvent();
        // then
        Assertions.assertEquals(INLINE_QUERY_COMMAND, event);
    }
}