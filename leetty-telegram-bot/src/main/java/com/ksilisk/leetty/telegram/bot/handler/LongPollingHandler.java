package com.ksilisk.leetty.telegram.bot.handler;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessorResolver;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

public abstract class LongPollingHandler extends Handler implements LongPollingBot {

    protected LongPollingHandler(LeettyProperties.Bot bot, LeettyProperties leettyProperties,
                                 UpdateProcessorResolver updateProcessorResolver,
                                 SenderResolver senderResolver) {
        super(bot, leettyProperties, updateProcessorResolver, senderResolver);
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        WebhookUtils.clearWebhook(sender);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public BotOptions getOptions() {
        return sender.getOptions();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}
