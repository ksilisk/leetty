package com.ksilisk.leetty.telegram.bot.keyboard;

import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SET_LEETCODE_PROFILE;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

public class SettingsMessageInlineKeyboard {
    public static final String AUTO_SENDING_DAILY_BUTTON_TEXT = "Auto sending daily question";
    public static final String SET_LEETCODE_PROFILE_BUTTON_TEXT = "Set LeetCode profile";

    public static InlineKeyboardMarkup create() {
        InlineKeyboardButton autoSendingDailyButton = InlineKeyboardButton.builder()
                .text(AUTO_SENDING_DAILY_BUTTON_TEXT)
                .callbackData(new CallbackData(UPDATE_SEND_DAILY_TIME).toString())
                .build();
        InlineKeyboardButton setLeetcodeProfileButton = InlineKeyboardButton.builder()
                .text(SET_LEETCODE_PROFILE_BUTTON_TEXT)
                .callbackData(new CallbackData(SET_LEETCODE_PROFILE).toString())
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(Collections.singletonList(autoSendingDailyButton))
                .keyboardRow(Collections.singletonList(setLeetcodeProfileButton))
                .build();
    }
}
