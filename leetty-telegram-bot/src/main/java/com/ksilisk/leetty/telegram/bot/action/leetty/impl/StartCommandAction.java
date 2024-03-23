package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.service.LeettyBotService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Setter
public class StartCommandAction implements LeettyAction {
    private final Sender sender;
    private final String messageText;
    private final LeettyBotService botService;

    public StartCommandAction(@Value("classpath:messages/start_message.txt") Resource messageResource,
                              SenderResolver senderResolver, LeettyBotService botService) throws IOException {
        this.sender = senderResolver.getSender(getBot());
        this.messageText = messageResource.getContentAsString(UTF_8);
        this.botService = botService;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        botService.addChat(message.getChat());
        botService.addUser(message.getFrom());
        SendMessage sendMessage = SendMessage.builder()
                .chatId(message.getChatId())
                .text(messageText)
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.START_COMMAND;
    }
}
