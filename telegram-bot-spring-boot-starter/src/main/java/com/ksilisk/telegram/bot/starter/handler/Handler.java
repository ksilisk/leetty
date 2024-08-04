package com.ksilisk.telegram.bot.starter.handler;


import com.ksilisk.telegram.bot.starter.config.BotProperties;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;

public abstract class Handler {
    protected final UpdateProcessor updateProcessor;
    protected final BotProperties botProperties;
    protected final Sender sender;

    protected Handler(BotProperties botProperties, UpdateProcessor updateProcessor, Sender sender) {
        this.updateProcessor = updateProcessor;
        this.botProperties = botProperties;
        this.sender = sender;
    }
}
