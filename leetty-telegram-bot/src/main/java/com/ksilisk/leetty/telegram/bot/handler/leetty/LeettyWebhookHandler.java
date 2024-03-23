package com.ksilisk.leetty.telegram.bot.handler.leetty;

import com.ksilisk.leetty.telegram.bot.annotation.WebhookBot;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties.Bot;
import com.ksilisk.leetty.telegram.bot.handler.WebhookHandler;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessorResolver;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@WebhookBot
class LeettyWebhookHandler extends WebhookHandler {
    private static final Bot BOT = Bot.LEETTY;

    public LeettyWebhookHandler(LeettyProperties leettyProperties,
                                UpdateProcessorResolver updateProcessorResolver,
                                SenderResolver senderResolver) {
        super(BOT, leettyProperties, updateProcessorResolver, senderResolver);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        updateProcessor.process(update);
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
