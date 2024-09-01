package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.GET_QUESTION_DIFFICULTY;

class GetQuestionDifficultyActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        GetQuestionDifficultyAction getQuestionDifficultyAction = new GetQuestionDifficultyAction(null, null, null);
        // when
        LeettyBotEvent event = getQuestionDifficultyAction.getEvent();
        // then
        Assertions.assertEquals(GET_QUESTION_DIFFICULTY, event);
    }
}