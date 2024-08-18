package com.ksilisk.telegram.bot.starter.processor;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public abstract class MessageUpdateProcessor implements UpdateProcessor {
    @Override
    public void process(Update update) {
        if (!update.hasMessage()) {
            log.debug("Update hasn't message. Skip.");
            return;
        }
        try {
            processUpdate(update);
        } catch (Exception exception) {
            log.error("Error while process update with message. Update: {}", update, exception);
        }
    }

    public abstract void processUpdate(Update update);
}
