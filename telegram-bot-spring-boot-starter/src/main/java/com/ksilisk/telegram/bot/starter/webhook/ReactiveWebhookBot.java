package com.ksilisk.telegram.bot.starter.webhook;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import reactor.core.publisher.Flux;

public interface ReactiveWebhookBot<T> extends WebhookBot {
    @Override
    default BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        throw new UnsupportedOperationException("This method not allowed");
    }

    Flux<UpdateProcessingResult> processFlux(Flux<T> flux);
}
