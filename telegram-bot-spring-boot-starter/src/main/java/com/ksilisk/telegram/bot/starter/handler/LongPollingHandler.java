package com.ksilisk.telegram.bot.starter.handler;

import com.ksilisk.telegram.bot.starter.config.BotProperties;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

@Slf4j
public abstract class LongPollingHandler extends Handler implements LongPollingBot {

    protected LongPollingHandler(BotProperties botProperties, UpdateProcessor updateProcessor, Sender sender) {
        super(botProperties, updateProcessor, sender);
    }

    protected abstract void processUpdate(Update update);

    @Override
    public void onUpdateReceived(Update update) {
        try {
            processUpdate(update);
        } catch (Exception ex) {
            log.warn("Error while process long polling update. Update: {}", update, ex);
        }
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
