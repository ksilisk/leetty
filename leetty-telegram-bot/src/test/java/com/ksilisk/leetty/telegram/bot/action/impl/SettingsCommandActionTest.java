package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SETTINGS_COMMAND;

class SettingsCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        SettingsCommandAction settingsCommandAction = new SettingsCommandAction(null, null);
        // when
        LeettyBotEvent event = settingsCommandAction.getEvent();
        // then
        Assertions.assertEquals(SETTINGS_COMMAND, event);
    }
}