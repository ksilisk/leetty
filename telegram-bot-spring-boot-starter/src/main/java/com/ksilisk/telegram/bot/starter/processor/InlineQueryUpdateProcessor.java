package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

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

    @Override
    public Flux<UpdateProcessingResult> processFlux(Flux<Update> updates) {
        Flux<Update> filteredUpdates = updates.filter(u -> {
            if (!u.hasInlineQuery()) {
                log.debug("Update hasn't inline query. Skip.");
            }
            return u.hasInlineQuery();
        }).doOnError(exception -> log.error("Error while processing update with inline query.", exception));
        return processFluxUpdate(filteredUpdates);
    }

    public abstract Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates);
}
