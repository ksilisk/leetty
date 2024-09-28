package com.ksilisk.telegram.bot.starter.handler;

import com.ksilisk.telegram.bot.starter.config.BotProperties;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import com.ksilisk.telegram.bot.starter.webhook.DefaultTelegramBotKafkaTopicConvention;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

class WebhookHandlerTest {
    @Test
    void checkSetWebhookIsInvoked_shouldInvokeSetWebhookMethod() throws TelegramApiException {
        // given
        Sender sender = Mockito.mock(Sender.class);
        UpdateProcessor updateProcessor = Mockito.mock(UpdateProcessor.class);
        SetWebhook setWebhook = Mockito.mock(SetWebhook.class);
        WebhookHandler webhookHandler = new WebhookHandler(new BotProperties(), updateProcessor, sender,
                new DefaultTelegramBotKafkaTopicConvention()) {
            @Override
            protected void processUpdate(Update update) {
            }
        };
        // then
        Assertions.assertThrows(NullPointerException.class, () -> webhookHandler.setWebhook(setWebhook));
        Mockito.verify(setWebhook).validate();
    }

    @Test
    void getBotWebhookBotPath_shouldReturnLowerCaseAndReplaced() {
        // given
        Sender sender = Mockito.mock(Sender.class);
        UpdateProcessor updateProcessor = Mockito.mock(UpdateProcessor.class);
        BotProperties botProperties = new BotProperties();
        botProperties.setUsername("Test_Bot-Hello_bot_AAAAA");
        WebhookHandler webhookHandler = new WebhookHandler(botProperties, updateProcessor, sender,
                new DefaultTelegramBotKafkaTopicConvention()) {
            @Override
            protected void processUpdate(Update update) {
            }
        };
        // when
        String botPath = webhookHandler.getBotPath();
        // then
        Assertions.assertEquals(botPath, "test-bot-hello-bot-aaaaa");
    }

    @Test
    void processUpdateThrowsException_shouldNotThrowException() {
        // given
        Sender sender = Mockito.mock(Sender.class);
        UpdateProcessor updateProcessor = Mockito.mock(UpdateProcessor.class);
        WebhookHandler webhookHandler = new WebhookHandler(new BotProperties(), updateProcessor, sender,
                new DefaultTelegramBotKafkaTopicConvention()) {
            @Override
            protected void processUpdate(Update update) {
                throw new RuntimeException("some ex");
            }
        };
        // then
        Assertions.assertDoesNotThrow(() -> webhookHandler.onWebhookUpdateReceived(new Update()));
    }
}