package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_TOPICS;

class GetQuestionTopicsActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionTopicsAction getQuestionTopicsAction = new GetQuestionTopicsAction(null, null, null);
        // when
        LeettyBotEvent event = getQuestionTopicsAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_TOPICS, event);
    }
}