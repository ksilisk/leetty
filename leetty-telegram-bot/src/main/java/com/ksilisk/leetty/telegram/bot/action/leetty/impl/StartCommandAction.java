package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.service.LeettyBotService;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
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
    private final LeettyBotService botService;

    public StartCommandAction(MessageSampleReader messageSampleReader, SenderResolver senderResolver,
                              LeettyBotService botService) {
        this.sender = senderResolver.getSender(getBot());
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
