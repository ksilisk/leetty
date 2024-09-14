package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.LeetCodeQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.RANDOM_QUESTION_COMMAND;

@Component
@RequiredArgsConstructor
public class RandomQuestionCommandAction extends LeettyAction {
    private final Sender sender;
    private final QuestionService questionService;
    private final LeetCodeQuestionMessagePreparer messagePreparer;

    @Override
    public void handle(Update update) {
        Question randomQuestion = questionService.getRandomQuestion();
        SendMessage sendMessage = messagePreparer.prepareMessage(randomQuestion, update.getMessage().getChatId());
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return RANDOM_QUESTION_COMMAND;
    }
}
