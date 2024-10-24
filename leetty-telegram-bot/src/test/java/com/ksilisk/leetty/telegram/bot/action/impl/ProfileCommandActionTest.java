package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.PROFILE_COMMAND;

class ProfileCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        ProfileCommandAction profileCommandAction =
                new ProfileCommandAction(null, null, null);
        // when
        LeettyBotEvent event = profileCommandAction.getEvent();
        // then
        Assertions.assertEquals(PROFILE_COMMAND, event);
    }

}