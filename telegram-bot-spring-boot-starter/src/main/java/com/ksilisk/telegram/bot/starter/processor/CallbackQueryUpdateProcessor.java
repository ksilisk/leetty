package com.ksilisk.telegram.bot.starter.processor;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public abstract class CallbackQueryUpdateProcessor implements UpdateProcessor {
    @Override
    public void process(Update update) {
        if (!update.hasCallbackQuery()) {
            log.debug("Update hasn't callback query. Skip.");
            return;
        }
        try {
            processUpdate(update);
        } catch (Exception ex) {
            log.error("Error while processing update with callback query. Update: {}", update, ex);
        }
    }

    public abstract void processUpdate(Update update);
}
