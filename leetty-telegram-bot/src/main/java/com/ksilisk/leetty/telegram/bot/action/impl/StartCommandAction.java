package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.LeettyFacade;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Setter
public class StartCommandAction implements LeettyAction {
    private static final String START_MESSAGE_SAMPLE_FILENAME = "start_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;
    private final LeettyFacade botService;

    public StartCommandAction(MessageSampleReader messageSampleReader, Sender sender,
                              LeettyFacade botService) {
        this.sender = sender;
        this.messageSampleReader = messageSampleReader;
        this.botService = botService;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        botService.addChat(message.getChat());
        botService.addUser(message.getFrom());
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(messageSampleReader.read(START_MESSAGE_SAMPLE_FILENAME))
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.START_COMMAND;
    }
}
