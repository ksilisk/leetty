package com.ksilisk.telegram.bot.starter.webhook;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;

@Slf4j
public class KafkaWebhookListener implements Webhook, MessageListener<String, Update> {
    private final WebhookBot webhookBot;

    public KafkaWebhookListener(WebhookBot webhookBot) {
        this.webhookBot = webhookBot;
    }

    @Override
    public void onMessage(ConsumerRecord<String, Update> data) {
        Update update = data.value();
        log.debug("Held update: {}", update);
        webhookBot.onWebhookUpdateReceived(update);
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
    }
}
