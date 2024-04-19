package com.ksilisk.leetty.telegram.bot.webhook.impl;

import com.ksilisk.leetty.telegram.bot.webhook.LeettyWebhook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;

class LeettyWebhookImplTest {
    static ThreadPoolTaskExecutor taskExecutor;

    @BeforeAll
    static void setUp() {
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.initialize();
    }

    @AfterAll
    static void setDown() {
        taskExecutor.initiateShutdown();
    }

    @Test
    void sendValidUpdateForExistsBot_shouldUpdateBeReceived() {
        // given
        Update update = new Update();
        WebhookBot webhookBot = Mockito.mock(WebhookBot.class);
        Mockito.when(webhookBot.getBotPath()).thenReturn("test");
        LeettyWebhook leettyWebhook = new LeettyWebhookImpl(taskExecutor);
        // when
        leettyWebhook.registerWebhook(webhookBot);
        leettyWebhook.updateReceived("test", update);
        // then
        Mockito.verify(webhookBot).onWebhookUpdateReceived(update);
    }

    @Test
    void sendValidUpdateForNonExistsBot_shouldNotThrowException() {
        // given
        Update update = new Update();
        LeettyWebhook leettyWebhook = new LeettyWebhookImpl(taskExecutor);
        // then
        Assertions.assertDoesNotThrow(() -> leettyWebhook.updateReceived("test", update));
    }

    @Test
    void sendInvalidUpdateFonNonExistsBot_shouldNotThrowException() {
        // given
        Update update = null;
        LeettyWebhook leettyWebhook = new LeettyWebhookImpl(taskExecutor);
        // then
        Assertions.assertDoesNotThrow(() -> leettyWebhook.updateReceived("test", update));
    }

    @Test
    void sendInvalidUpdateForExistsBot_shouldNotThrowException() {
        // given
        Update update = null;
        LeettyWebhook leettyWebhook = new LeettyWebhookImpl(taskExecutor);
        WebhookBot webhookBot = Mockito.mock(WebhookBot.class);
        Mockito.when(webhookBot.getBotPath()).thenReturn("test");
        // when
        leettyWebhook.registerWebhook(webhookBot);
        // then
        Assertions.assertDoesNotThrow(() -> leettyWebhook.updateReceived("test", update));
    }
}