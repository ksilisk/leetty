package com.ksilisk.leetty.telegram.bot.webhook.impl;

import com.ksilisk.leetty.telegram.bot.webhook.LeettyWebhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class LeettyWebhookImpl implements LeettyWebhook, Closeable {
    private final Map<String, WebhookBot> webhookBotMap = new ConcurrentHashMap<>();
    private final ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void updateReceived(String botPath, Update update) {
        taskExecutor.submitCompletable(() -> {
            if (webhookBotMap.containsKey(botPath)) {
                webhookBotMap.get(botPath).onWebhookUpdateReceived(update);
            } else {
                log.warn("Bot with path: '{}' not present in webhook bots. Skip update", botPath);
            }
        }).whenComplete((o, throwable) -> {
            if (throwable != null) {
                log.warn("Error while process update for botPath: '{}'. Update: {}", botPath, update, throwable);
            }
        });
    }

    @Override
    public void registerWebhook(WebhookBot webhookBot) {
        webhookBotMap.putIfAbsent(webhookBot.getBotPath(), webhookBot);
    }

    @Override
    public void close() {
        taskExecutor.initiateShutdown();
    }
}
