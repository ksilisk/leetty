package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

@Component
public class SettingsCommandAction implements LeettyAction {
    private static final String SETTINGS_MESSAGE_SAMPLE_FILENAME = "settings_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;

    public SettingsCommandAction(MessageSampleReader messageSampleReader, SenderResolver senderResolver) {
        this.sender = senderResolver.getSender(getBot());
        this.messageSampleReader = messageSampleReader;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageSampleReader.read(SETTINGS_MESSAGE_SAMPLE_FILENAME))
                .replyMarkup(getKeyboard())
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.SETTINGS_COMMAND;
    }

    private ReplyKeyboard getKeyboard() {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Отправка ежедневного задания по расписанию")
                .callbackData(new CallbackData(UPDATE_SEND_DAILY_TIME).toString())
                .build();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(Collections.singletonList(button))
                .build();
    }
}
