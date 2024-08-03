package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.util.DailyQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.service.LeettyBotService;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.DAILY_QUESTION_COMMAND;

@Component
@Getter
public class DailyQuestionCommandAction implements LeettyAction {
    private final Sender sender;
    private final LeettyBotService botService;
    private final DailyQuestionMessagePreparer dailyQuestionMessagePreparer;

    public DailyQuestionCommandAction(Sender sender, LeettyBotService leettyBotService,
                                      DailyQuestionMessagePreparer dailyQuestionMessagePreparer) {
        this.sender = sender;
        this.botService = leettyBotService;
        this.dailyQuestionMessagePreparer = dailyQuestionMessagePreparer;
    }

    @Override
    public void execute(Update update) {
        DailyCodingQuestion dailyQuestion = botService.getDailyQuestion();
        sender.execute(dailyQuestionMessagePreparer.prepareMessage(dailyQuestion, update.getMessage().getChatId()));
    }

    @Override
    public LeettyBotEvent getEvent() {
        return DAILY_QUESTION_COMMAND;
    }
}
