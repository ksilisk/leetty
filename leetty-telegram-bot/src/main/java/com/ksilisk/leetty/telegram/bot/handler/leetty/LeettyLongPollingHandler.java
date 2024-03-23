package com.ksilisk.leetty.telegram.bot.handler.leetty;

import com.ksilisk.leetty.telegram.bot.annotation.LongPollingBot;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.handler.LongPollingHandler;
import com.ksilisk.leetty.telegram.bot.processor.UpdateProcessorResolver;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import org.telegram.telegrambots.meta.api.objects.Update;

@LongPollingBot
class LeettyLongPollingHandler extends LongPollingHandler {
    private static final LeettyProperties.Bot BOT = LeettyProperties.Bot.LEETTY;

    protected LeettyLongPollingHandler(LeettyProperties leettyProperties,
                                       UpdateProcessorResolver updateProcessorResolver,
                                       SenderResolver senderResolver) {
        super(BOT, leettyProperties, updateProcessorResolver, senderResolver);
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.process(update);
    }
}
