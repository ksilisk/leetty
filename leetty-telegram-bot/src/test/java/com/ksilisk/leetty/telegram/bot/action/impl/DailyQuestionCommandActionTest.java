package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.DAILY_QUESTION_COMMAND;

class DailyQuestionCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        DailyQuestionCommandAction dailyQuestionCommandAction = new DailyQuestionCommandAction(null, null, null);
        // when
        LeettyBotEvent event = dailyQuestionCommandAction.getEvent();
        // then
        Assertions.assertEquals(DAILY_QUESTION_COMMAND, event);
    }
}