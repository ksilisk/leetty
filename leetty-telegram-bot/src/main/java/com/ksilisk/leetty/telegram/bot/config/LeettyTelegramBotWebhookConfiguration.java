package com.ksilisk.leetty.telegram.bot.config;

import com.ksilisk.leetty.telegram.bot.webhook.LeettyWebhook;
import com.ksilisk.leetty.telegram.bot.webhook.impl.LeettyWebhookImpl;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
@ConditionalOnProperty(prefix = "leetty", name = "webhook.enabled", havingValue = "true")
public class LeettyTelegramBotWebhookConfiguration {
    @Bean
    @Validated
    @ConfigurationProperties(prefix = "leetty.webhook")
    public WebhookProperties webhookProperties() {
        return new WebhookProperties();
    }

    @Bean
    public LeettyWebhook webhook(WebhookProperties webhookProperties) {
        return new LeettyWebhookImpl(createTaskExecutor(webhookProperties.getExecutorConfig()));
    }

    @Bean
    public TelegramBotsApi webhookBotsApi(Webhook webhook, List<WebhookBot> telegramBots,
                                          WebhookProperties webhookProperties) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
        for (WebhookBot bot : telegramBots) {
            telegramBotsApi.registerBot(bot, createSetWebhook(webhookProperties));
        }
        return telegramBotsApi;
    }

    @Bean("webhookFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/callback/**").permitAll()
                                .anyRequest().authenticated());
        return http.build();
    }

    private ThreadPoolTaskExecutor createTaskExecutor(WebhookProperties.WebhookExecutorConfig executorConfig) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(executorConfig.getCorePoolSize());
        taskExecutor.setMaxPoolSize(executorConfig.getMaxPoolSize());
        taskExecutor.setQueueCapacity(executorConfig.getQueueCapacity());
        taskExecutor.setThreadFactory(Executors.defaultThreadFactory());
        taskExecutor.initialize();
        return taskExecutor;
    }

    private SetWebhook createSetWebhook(WebhookProperties webhookProperties) {
        SetWebhook.SetWebhookBuilder builder = SetWebhook.builder();
        InputFile certificateFile = new InputFile(new File(webhookProperties.getCertificate()));
        builder.url(webhookProperties().getExternalUrl());
        builder.dropPendingUpdates(webhookProperties.isDropPendingUpdates());
        builder.certificate(certificateFile);
        builder.maxConnections(webhookProperties.getMaxConnections());
        if (StringUtils.hasText(webhookProperties.getSecretToken())) {
            builder.secretToken(webhookProperties.getSecretToken());
        }
        if (StringUtils.hasText(webhookProperties.getIpAddress())) {
            builder.ipAddress(webhookProperties.getIpAddress());
        }
        return builder.build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class WebhookProperties {
        @NotBlank
        private String certificate;
        @NotBlank
        private String externalUrl;
        private String ipAddress;
        private String secretToken;
        private boolean dropPendingUpdates;
        @Min(1)
        @Max(100)
        private int maxConnections = 20;
        private WebhookExecutorConfig executorConfig = new WebhookExecutorConfig();

        @Getter
        @Setter
        @NoArgsConstructor
        private static class WebhookExecutorConfig {
            @Min(1)
            private int corePoolSize = 1;
            @Min(1)
            private int maxPoolSize = 5;
            @Min(100)
            private int queueCapacity = 10000;
        }

    }
}
