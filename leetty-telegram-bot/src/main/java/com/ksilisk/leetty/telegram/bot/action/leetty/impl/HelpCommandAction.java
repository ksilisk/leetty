package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.HELP_COMMAND;

@Component
public class HelpCommandAction implements LeettyAction {
    private final Sender sender;
    private final String messageText;

    public HelpCommandAction(@Value("classpath:messages/help_message.txt") Resource messageResource,
                             SenderResolver senderResolver) throws IOException {
        this.sender = senderResolver.getSender(getBot());
        this.messageText = messageResource.getContentAsString(StandardCharsets.UTF_8);
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageText)
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return HELP_COMMAND;
    }
}
