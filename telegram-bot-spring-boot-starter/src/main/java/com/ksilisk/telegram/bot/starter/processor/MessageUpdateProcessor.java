package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

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

    @Override
    public Flux<UpdateProcessingResult> processFlux(Flux<Update> updates) {
        log.info("At update processor");
        Flux<Update> filteredUpdates = updates.filter(u -> {
            log.info("Processing update: {}", u);
            if (!u.hasMessage()) {
                log.debug("Update hasn't message. Skip.");
            }
            return u.hasMessage();
        }).doOnError(exception -> log.error("Error while process update with message.", exception));
        return processFluxUpdate(filteredUpdates);
    }

    public abstract Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates);
}
