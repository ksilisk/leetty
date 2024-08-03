package com.ksilisk.telegram.bot.starter.sender;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
public abstract class Sender extends DefaultAbsSender {
    protected Sender(String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    protected Sender(DefaultBotOptions botOptions, String botToken) {
        super(botOptions, botToken);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        try {
            return super.execute(method);
        } catch (TelegramApiException e) {
            log.warn("Error while sender executing bot api method. Method: {}", method.toString(), e);
            throw new IllegalStateException(e);
        }
    }
}
