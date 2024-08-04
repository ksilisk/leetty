package com.ksilisk.telegram.bot.starter.handler;

import com.ksilisk.telegram.bot.starter.config.BotProperties;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.util.WebhookUtils;

@Slf4j
public abstract class WebhookHandler extends Handler implements WebhookBot {
    protected WebhookHandler(BotProperties botProperties, UpdateProcessor updateProcessor, Sender sender) {
        super(botProperties, updateProcessor, sender);
    }

    protected abstract void processUpdate(Update update);

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

    @Override
    public String getBotPath() {
        return botProperties.getUsername().toLowerCase().replaceAll("_", "-");
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            processUpdate(update);
        } catch (Exception ex) {
            log.warn("Error while process webhook update. Update: {}", update, ex);
        }
        return null;
    }

}
