package com.ksilisk.telegram.bot.starter.webhook;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;

class KafkaWebhookListenerTest {
    @Test
    void processingMessage_shouldProcess() {
        // given
        WebhookBot bot = Mockito.mock(WebhookBot.class);
        KafkaWebhookListener webhookListener = new KafkaWebhookListener(bot);
        Update update = new Update();
        update.setUpdateId(1);
        ConsumerRecord<String, Update> record = new ConsumerRecord<>("test", 0, 0, "key", update);
        // when
        webhookListener.onMessage(record);
        // then
        Mockito.verify(bot).onWebhookUpdateReceived(update);
    }
}