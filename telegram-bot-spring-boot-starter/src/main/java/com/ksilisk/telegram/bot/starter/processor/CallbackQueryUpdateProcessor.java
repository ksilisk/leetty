package com.ksilisk.telegram.bot.starter.processor;

import com.ksilisk.telegram.bot.starter.webhook.UpdateProcessingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachineEventResult;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;

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

    @Override
    public Flux<UpdateProcessingResult> processFlux(Flux<Update> updates) {
        Flux<Update> filteredUpdates = updates.filter(u -> {
            if (!u.hasCallbackQuery()) {
                log.debug("Update hasn't callback query. Skip.");
            }
            return u.hasCallbackQuery();
        }).doOnError(exception -> log.error("Error while processing update with callback query", exception));
        return processFluxUpdate(filteredUpdates);
    }

    public abstract Flux<UpdateProcessingResult> processFluxUpdate(Flux<Update> updates);
}
