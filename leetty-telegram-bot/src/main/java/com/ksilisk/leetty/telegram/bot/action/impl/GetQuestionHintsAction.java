package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.question.QuestionHints;
import com.ksilisk.leetty.telegram.bot.action.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.keyboard.QuestionMessageInlineKeyboard;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

import static com.ksilisk.leetty.telegram.bot.keyboard.QuestionMessageInlineKeyboard.HINTS_BUTTON_TEXT;

@Component
@RequiredArgsConstructor
public class GetQuestionHintsAction extends LeettyCallbackAction {
    private static final String HINTS_FORMATTER = "Hints: \n\n%s";

    private final QuestionService questionService;
    private final LeetCodeUrlParser urlParser;
    private final Sender sender;

    @Override
    public void handle(Update update, CallbackData callbackData) {
        Message message = (Message) update.getCallbackQuery().getMessage();
        String questionUrl = QuestionMessageInlineKeyboard.getLeetCodeUrlFromKeyboard(message.getReplyMarkup());
        String questionTitleSlug = urlParser.parseTitleSlug(questionUrl);
        QuestionHints questionHints = questionService.getHints(questionTitleSlug);
        InlineKeyboardMarkup keyboardMarkup = message.getReplyMarkup();
        QuestionMessageInlineKeyboard.removeButton(keyboardMarkup, HINTS_BUTTON_TEXT);
        EditMessageText editMessageText = EditMessageText.builder()
                .messageId(message.getMessageId())
                .chatId(message.getChatId())
                .replyMarkup(keyboardMarkup)
                .text(message.getText() + "\n\n" + String.format(HINTS_FORMATTER, prepareHints(questionHints.hints())))
                .build();
        sender.execute(editMessageText);
    }

    private String prepareHints(List<String> hints) {
        StringBuilder hintsBuilder = new StringBuilder();
        for (int hintId = 0; hintId < hints.size(); hintId++) {
            hintsBuilder.append(hintId + 1).append(") ");
            hintsBuilder.append(hints.get(hintId));
            if (hintId < hints.size() - 1) {
                hintsBuilder.append("\n");
            }
        }
        return hintsBuilder.toString();
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.GET_QUESTION_HINTS;
    }
}
