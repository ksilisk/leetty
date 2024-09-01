package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_HINTS;

class GetQuestionHintsActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionHintsAction getQuestionHintsAction = new GetQuestionHintsAction(null, null, null);
        // when
        LeettyBotEvent event = getQuestionHintsAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_HINTS, event);
    }
}