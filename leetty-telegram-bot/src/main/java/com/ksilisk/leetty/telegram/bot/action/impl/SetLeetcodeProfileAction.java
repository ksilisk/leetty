package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyCallbackAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.payload.CallbackData;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetLeetcodeProfileAction extends LeettyCallbackAction {
    private static final String SET_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME = "set_leetcode_profile_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;

    @Override
    public void handle(Update update, CallbackData callbackData) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String message = messageSampleReader.read(SET_LEETCODE_PROFILE_MESSAGE_SAMPLE_FILENAME);
        EditMessageText editMessageText =
                EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .text(message)
                        .build();
        sender.execute(editMessageText);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.SET_LEETCODE_PROFILE;
    }
}
