package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.keyboard.SettingsMessageInlineKeyboard;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

@Component
@RequiredArgsConstructor
public class SettingsCommandAction extends LeettyAction {
    private static final String SETTINGS_MESSAGE_SAMPLE_FILENAME = "settings_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;

    @Override
    public void handle(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageSampleReader.read(SETTINGS_MESSAGE_SAMPLE_FILENAME))
                .replyMarkup(SettingsMessageInlineKeyboard.create())
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.SETTINGS_COMMAND;
    }
}
