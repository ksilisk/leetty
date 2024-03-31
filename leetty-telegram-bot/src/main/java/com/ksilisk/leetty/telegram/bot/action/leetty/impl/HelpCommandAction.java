package com.ksilisk.leetty.telegram.bot.action.leetty.impl;

import com.ksilisk.leetty.telegram.bot.action.leetty.LeettyAction;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.HELP_COMMAND;

@Component
public class HelpCommandAction implements LeettyAction {
    private static final String HELP_MESSAGE_SAMPLE_FILENAME = "help_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;

    public HelpCommandAction(MessageSampleReader messageSampleReader, SenderResolver senderResolver) {
        this.sender = senderResolver.getSender(getBot());
        this.messageSampleReader = messageSampleReader;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(messageSampleReader.read(HELP_MESSAGE_SAMPLE_FILENAME))
                .build();
        sender.execute(sendMessage);
    }

    @Override
    public LeettyBotEvent getEvent() {
        return HELP_COMMAND;
    }
}
