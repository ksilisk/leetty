package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.LeetCodeQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.DAILY_QUESTION_COMMAND;

@Component
@RequiredArgsConstructor
public class DailyQuestionCommandAction extends LeettyAction {
    private final Sender sender;
    private final QuestionService questionService;
    private final LeetCodeQuestionMessagePreparer leetCodeQuestionMessagePreparer;

    @Override
    public void handle(Update update) {
        DailyCodingQuestion dailyQuestion = questionService.getDailyQuestion();
        sender.execute(leetCodeQuestionMessagePreparer.prepareMessage(dailyQuestion.getQuestion(), update.getMessage().getChatId()));
    }

    @Override
    public LeettyBotEvent getEvent() {
        return DAILY_QUESTION_COMMAND;
    }
}
