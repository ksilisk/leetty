package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.service.LeettyFacade;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SetSendDailyTimeAction implements LeettyCallbackAction {
    public static final String TIME_CALLBACK_DATA_KEY = "time";

    private final Sender sender;
    private final LeettyFacade leettyFacade;

    @Override
    public void execute(Update update, CallbackData callbackData) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        leettyFacade.updateTimeToSendDailyQuestion(callbackQuery.getMessage().getChatId(),
                callbackData.getKey(TIME_CALLBACK_DATA_KEY).toString());
        EditMessageText editMessageText = EditMessageText.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .text("Время отправки успешно установлено!").build();
        sender.execute(editMessageText);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.SET_SEND_DAILY_TIME;
    }
}
