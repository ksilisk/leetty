package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.HELP_COMMAND;

class HelpCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        HelpCommandAction helpCommandAction = new HelpCommandAction(null, null);
        // when
        LeettyBotEvent event = helpCommandAction.getEvent();
        // then
        Assertions.assertEquals(HELP_COMMAND, event);
    }
}