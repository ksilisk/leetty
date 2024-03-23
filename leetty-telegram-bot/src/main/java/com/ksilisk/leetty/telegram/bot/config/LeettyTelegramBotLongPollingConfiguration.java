package com.ksilisk.leetty.telegram.bot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "leetty", name = "webhook.enabled", havingValue = "false", matchIfMissing = true)
public class LeettyTelegramBotLongPollingConfiguration {
    @Bean
    @ConditionalOnMissingBean({TelegramBotsApi.class})
    public TelegramBotsApi longPollingBotsApi(List<LongPollingBot> telegramBots) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        for (LongPollingBot bot : telegramBots) {
            telegramBotsApi.registerBot(bot);
        }
        return telegramBotsApi;
    }
}
