package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SET_SEND_DAILY_TIME;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

@Component
public class UpdateSendDailyTimeAction implements LeettyCallbackAction {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DISABLE_SENDING_DAILY_DATA = "";

    public static List<LocalTime> TIMES_TO_SEND = List.of(
            LocalTime.of(10, 0),
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            LocalTime.of(22, 0));

    private final Sender sender;

    public UpdateSendDailyTimeAction(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(Update update, CallbackData callbackData) {
        update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .text("Выберите в какое время присылать задачу:")
                .replyMarkup((InlineKeyboardMarkup) replyKeyboard())
                .build();
        sender.execute(editMessageText);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return UPDATE_SEND_DAILY_TIME;
    }

    private ReplyKeyboard replyKeyboard() {
        InlineKeyboardButton disableSendingButton = InlineKeyboardButton.builder()
                .text("Отключить отправку!")
                .callbackData(new CallbackData(SET_SEND_DAILY_TIME,
                        Map.of(SetSendDailyTimeAction.TIME_CALLBACK_DATA_KEY, DISABLE_SENDING_DAILY_DATA)).toString())
                .build();
        List<InlineKeyboardButton> timeButtons = TIMES_TO_SEND.stream()
                .map(time -> time.format(TIME_FORMATTER))
                .map(time -> InlineKeyboardButton.builder()
                        .text(time)
                        .callbackData(new CallbackData(SET_SEND_DAILY_TIME,
                                Map.of(SetSendDailyTimeAction.TIME_CALLBACK_DATA_KEY, time)).toString())
                        .build())
                .toList();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(timeButtons.get(0), timeButtons.get(1)))
                .keyboardRow(List.of(timeButtons.get(2), timeButtons.get(3)))
                .keyboardRow(List.of(disableSendingButton))
                .build();
    }
}
