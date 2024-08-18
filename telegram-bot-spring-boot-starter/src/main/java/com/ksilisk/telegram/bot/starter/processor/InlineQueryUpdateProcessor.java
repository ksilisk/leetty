package com.ksilisk.telegram.bot.starter.processor;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public abstract class InlineQueryUpdateProcessor implements UpdateProcessor {
    @Override
    public void process(Update update) {
        if (!update.hasInlineQuery()) {
            log.debug("Update hasn't inline query. Skip.");
            return;
        }
        try {
            processUpdate(update);
        } catch (Exception ex) {
            log.error("Error while processing update with inline query. Update: {}", update, ex);
        }
    }

    public abstract void processUpdate(Update update);
}
