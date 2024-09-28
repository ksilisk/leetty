package com.ksilisk.telegram.bot.starter.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
public class ReactiveKafkaWebhookListener implements Webhook {
    private final ReactiveWebhookBot<ReceiverRecord<String, Update>> reactiveWebhookBot;
    private final KafkaReceiver<String, Update> kafkaReceiver;

    public ReactiveKafkaWebhookListener(ReactiveWebhookBot<ReceiverRecord<String, Update>> reactiveWebhookBot,
                                        KafkaReceiver<String, Update> kafkaReceiver) {
        this.reactiveWebhookBot = reactiveWebhookBot;
        this.kafkaReceiver = kafkaReceiver;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        log.info("Starting reactive webhook listener");
        reactiveWebhookBot.processFlux(kafkaReceiver.receive().log()).subscribe(u -> log.info("Value: {}", u.toString())); // todo may be not logging received updates
    }

    @Override
    public void startServer() {
        // ignored
    }

    @Override
    public void registerWebhook(WebhookBot callback) {
        // ignored
    }

    @Override
    public void setInternalUrl(String internalUrl) {
        // ignored
    }

    @Override
    public void setKeyStore(String keyStore, String keyStorePassword) {
        // ignored
//        throw new UnsupportedOperationException("Method not allowed at this implementation");
    }
}
