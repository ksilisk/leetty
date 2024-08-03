package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InvalidCommandAction implements LeettyAction {
    private static final String INVALID_COMMAND_MESSAGE_SAMPLE_FILENAME = "invalid_command_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;

    public InvalidCommandAction(MessageSampleReader messageSampleReader, Sender sender) {
        this.sender = sender;
        this.messageSampleReader = messageSampleReader;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageSampleReader.read(INVALID_COMMAND_MESSAGE_SAMPLE_FILENAME))
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.INVALID;
    }
}
