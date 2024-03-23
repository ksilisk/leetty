package com.ksilisk.leetty.telegram.bot.handler;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessorResolver;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

public abstract class WebhookHandler extends Handler implements WebhookBot {

    protected WebhookHandler(LeettyProperties.Bot bot,
                             LeettyProperties leettyProperties,
                             UpdateProcessorResolver updateProcessorResolver,
                             SenderResolver senderResolver) {
        super(bot, leettyProperties, updateProcessorResolver, senderResolver);
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
        WebhookUtils.setWebhook(sender, this, setWebhook);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}
