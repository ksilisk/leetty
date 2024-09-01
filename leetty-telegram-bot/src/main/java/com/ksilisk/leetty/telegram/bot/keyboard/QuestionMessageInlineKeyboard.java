package com.ksilisk.leetty.telegram.bot.keyboard;

import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.*;

public class QuestionMessageInlineKeyboard {
    private static final String LEETCODE_QUESTION_URL_FORMAT = "https://leetcode.com/problems/%s/description/";

    public static final String EXAMPLES_BUTTON_TEXT = "Examples";
    public static final String CONSTRAINTS_BUTTON_TEXT = "Constraints";
    public static final String DIFFICULTY_BUTTON_TEXT = "Difficulty";
    public static final String ACCEPTANCE_BUTTON_TEXT = "Acceptance";
    public static final String TOPICS_BUTTON_TEXT = "Topics";
    public static final String HINTS_BUTTON_TEXT = "Hints";

    public static String getLeetCodeUrlFromKeyboard(InlineKeyboardMarkup keyboardMarkup) {
        List<List<InlineKeyboardButton>> keyboard = keyboardMarkup.getKeyboard();
        return keyboard.get(keyboard.size() - 1).get(0).getUrl();
    }

    public static void removeButton(InlineKeyboardMarkup keyboardMarkup, String buttonText) {
        for (List<InlineKeyboardButton> row : keyboardMarkup.getKeyboard()) {
            int indexForRemove = -1;
            for (int index = 0; index < row.size(); index++) {
                if (buttonText.equals(row.get(index).getText())) {
                    indexForRemove = index;
                }
            }
            if (indexForRemove != -1) {
                row.remove(indexForRemove);
                return;
            }
        }
    }

    public static InlineKeyboardMarkup forQuestion(Question question) {
        List<InlineKeyboardButton> firstRow = List.of(
                InlineKeyboardButton.builder()
                        .callbackData(new CallbackData(GET_QUESTION_EXAMPLES).toString())
                        .text(EXAMPLES_BUTTON_TEXT)
                        .build(),
                InlineKeyboardButton.builder()
                        .callbackData(new CallbackData(GET_QUESTION_CONSTRAINTS).toString())
                        .text(CONSTRAINTS_BUTTON_TEXT)
                        .build());
        List<InlineKeyboardButton> secondRow = List.of(
                InlineKeyboardButton.builder()
                        .callbackData(new CallbackData(GET_QUESTION_DIFFICULTY).toString())
                        .text(DIFFICULTY_BUTTON_TEXT)
                        .build(),
                InlineKeyboardButton.builder()
                        .callbackData(new CallbackData(GET_QUESTION_ACCEPTANCE).toString())
                        .text(ACCEPTANCE_BUTTON_TEXT)
                        .build());
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(InlineKeyboardButton.builder()
                .callbackData(new CallbackData(GET_QUESTION_TOPICS).toString())
                .text(TOPICS_BUTTON_TEXT)
                .build());
        if (question.getHints() != null && question.getHints().size() > 0) {
            thirdRow.add(InlineKeyboardButton.builder()
                    .callbackData(new CallbackData(GET_QUESTION_HINTS).toString())
                    .text(HINTS_BUTTON_TEXT)
                    .build());
        }
        List<InlineKeyboardButton> fourthRow = List.of(
                InlineKeyboardButton.builder()
                        .text("Open at LeetCode")
                        .url(String.format(LEETCODE_QUESTION_URL_FORMAT, question.getTitleSlug()))
                        .build());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(firstRow)
                .keyboardRow(secondRow)
                .keyboardRow(thirdRow)
                .keyboardRow(fourthRow)
                .build();
    }
}
