package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.question.QuestionContent;
import com.ksilisk.leetty.telegram.bot.action.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.keyboard.QuestionMessageInlineKeyboard;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionContentParser;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.ksilisk.leetty.telegram.bot.keyboard.QuestionMessageInlineKeyboard.CONSTRAINTS_BUTTON_TEXT;

@Component
@RequiredArgsConstructor
public class GetQuestionConstraintsAction extends LeettyCallbackAction {
    private static final String CONSTRAINTS_FORMATTER = "Constraints: \n\n%s";

    private final QuestionService questionService;
    private final LeetCodeUrlParser urlParser;
    private final LeetCodeQuestionContentParser contentParser;
    private final Sender sender;

    @Override
    public void handle(Update update, CallbackData callbackData) {
        Message message = (Message) update.getCallbackQuery().getMessage();
        String questionUrl = QuestionMessageInlineKeyboard.getLeetCodeUrlFromKeyboard(message.getReplyMarkup());
        String questionTitleSlug = urlParser.parseTitleSlug(questionUrl);
        QuestionContent questionContent = questionService.getContent(questionTitleSlug);
        String constraints = contentParser.parseConstraints(questionContent.content());
        InlineKeyboardMarkup keyboardMarkup = message.getReplyMarkup();
        QuestionMessageInlineKeyboard.removeButton(keyboardMarkup, CONSTRAINTS_BUTTON_TEXT);
        EditMessageText editMessageText = EditMessageText.builder()
                .messageId(message.getMessageId())
                .chatId(message.getChatId())
                .replyMarkup(keyboardMarkup)
                .text(message.getText() + "\n\n" + String.format(CONSTRAINTS_FORMATTER, constraints))
                .build();
        sender.execute(editMessageText);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.GET_QUESTION_CONSTRAINTS;
    }
}
