package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.RANDOM_QUESTION_COMMAND;

class RandomQuestionCommandActionTest {
    @Test
    void getEvent_shouldReturnValidEvent() {
        // given
        RandomQuestionCommandAction getQuestionDifficultyAction =
                new RandomQuestionCommandAction(null, null, null);
        // when
        LeettyBotEvent event = getQuestionDifficultyAction.getEvent();
        // then
        Assertions.assertEquals(RANDOM_QUESTION_COMMAND, event);
    }
}