package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.ChatService;
import com.ksilisk.leetty.telegram.bot.service.UserService;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommandAction extends LeettyAction {
    private static final String START_MESSAGE_SAMPLE_FILENAME = "start_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;
    private final ChatService chatService;
    private final UserService userService;

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        chatService.addChat(message.getChat());
        userService.addUser(message.getFrom());
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
