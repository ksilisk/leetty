package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.LeettyFacade;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionMessagePreparer;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.DAILY_QUESTION_COMMAND;

@Component
@RequiredArgsConstructor
public class DailyQuestionCommandAction implements LeettyAction {
    private final Sender sender;
    private final LeettyFacade botService;
    private final LeetCodeQuestionMessagePreparer leetCodeQuestionMessagePreparer;

    @Override
    public void execute(Update update) {
        DailyCodingQuestion dailyQuestion = botService.getDailyQuestion();
        sender.execute(leetCodeQuestionMessagePreparer.prepareMessage(dailyQuestion.getQuestion(), update.getMessage().getChatId()));
    }

    @Override
    public LeettyBotEvent getEvent() {
        return DAILY_QUESTION_COMMAND;
    }
}
