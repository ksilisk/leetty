package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.service.CallbackData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

@Component
public class SettingsCommandAction implements LeettyAction {
    private final Sender sender;
    private final String messageText;

    public SettingsCommandAction(@Value("classpath:messages/settings_message.txt") Resource messageResource,
                                 SenderResolver senderResolver) throws IOException {
        this.sender = senderResolver.getSender(getBot());
        this.messageText = messageResource.getContentAsString(StandardCharsets.UTF_8);
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageText)
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
