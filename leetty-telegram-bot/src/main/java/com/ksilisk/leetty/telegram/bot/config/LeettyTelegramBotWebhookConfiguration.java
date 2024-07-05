package com.ksilisk.leetty.telegram.bot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "leetty", name = "webhook.enabled", havingValue = "true")
@Profile({"dev", "prod"})
public class LeettyTelegramBotWebhookConfiguration {

    @Bean
    @ConfigurationProperties("leetty.webhook")
    public WebhookProperties webhookProperties() {
        return new WebhookProperties();
    }

    @Bean
    public Webhook webhook(WebhookProperties webhookProperties) {
        // todo implement
        return null;
    }

    @Bean
    public TelegramBotsApi webhookBotsApi(Webhook webhook, List<WebhookBot> telegramBots) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
        for (WebhookBot bot : telegramBots) {
            telegramBotsApi.registerBot(bot, new SetWebhook(""));
        }
        return telegramBotsApi;
    }

    private static class WebhookProperties {
        public WebhookProperties() {
        }
    }
}
