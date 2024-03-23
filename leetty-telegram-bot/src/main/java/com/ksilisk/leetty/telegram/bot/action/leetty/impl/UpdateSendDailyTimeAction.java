package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.service.CallbackData;
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

import static com.ksilisk.leetty.telegram.bot.action.leetty.impl.SetSendDailyTimeAction.TIME_CALLBACK_DATA_KEY;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SET_SEND_DAILY_TIME;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.UPDATE_SEND_DAILY_TIME;

@Component
public class UpdateSendDailyTimeAction implements LeettyCallbackAction {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static List<LocalTime> TIMES_TO_SEND = List.of(
            LocalTime.of(10, 0),
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            LocalTime.of(22, 0));

    private final Sender sender;

    public UpdateSendDailyTimeAction(SenderResolver senderResolver) {
        this.sender = senderResolver.getSender(getBot());
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
        List<InlineKeyboardButton> buttons = TIMES_TO_SEND.stream()
                .map(time -> time.format(TIME_FORMATTER))
                .map(time -> InlineKeyboardButton.builder()
                        .text(time)
                        .callbackData(new CallbackData(SET_SEND_DAILY_TIME, Map.of(TIME_CALLBACK_DATA_KEY, time)).toString())
                        .build())
                .toList();
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(buttons.get(0), buttons.get(1)))
                .keyboardRow(List.of(buttons.get(2), buttons.get(3)))
                .build();
    }
}
