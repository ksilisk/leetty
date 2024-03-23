package com.ksilisk.leetty.telegram.bot.handler;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties.BotProperties;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessor;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessorResolver;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;

public abstract class Handler {
    protected final LeettyProperties.Bot bot;
    protected final UpdateProcessor<?, ?> updateProcessor;
    protected final BotProperties botProperties;
    protected final Sender sender;

    protected Handler(LeettyProperties.Bot bot,
                      LeettyProperties leettyProperties,
                      UpdateProcessorResolver updateProcessorResolver,
                      SenderResolver senderResolver) {
        this.updateProcessor = updateProcessorResolver.getUpdateProcessorByBot(bot);
        this.botProperties = leettyProperties.getBotProperties().get(bot);
        this.bot = bot;
        this.sender = senderResolver.getSender(bot);
    }

    public LeettyProperties.Bot getBot() {
        return bot;
    }
}
