package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_CONSTRAINTS;

class GetQuestionConstraintsActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionConstraintsAction getQuestionConstraintsAction = new GetQuestionConstraintsAction(null, null, null, null);
        // when
        LeettyBotEvent event = getQuestionConstraintsAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_CONSTRAINTS, event);
    }
}