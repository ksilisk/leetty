package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_EXAMPLES;

class GetQuestionExamplesActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionExamplesAction getQuestionExamplesAction = new GetQuestionExamplesAction(null, null, null, null);
        // when
        LeettyBotEvent event = getQuestionExamplesAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_EXAMPLES, event);
    }
}