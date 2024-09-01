package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_ACCEPTANCE;

class GetQuestionAcceptanceActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionAcceptanceAction getQuestionAcceptanceAction = new GetQuestionAcceptanceAction(null, null, null);
        // when
        LeettyBotEvent event = getQuestionAcceptanceAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_ACCEPTANCE, event);
    }
}