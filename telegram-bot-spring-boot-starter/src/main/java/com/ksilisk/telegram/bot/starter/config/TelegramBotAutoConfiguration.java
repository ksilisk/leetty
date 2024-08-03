package com.ksilisk.telegram.bot.starter.config;

import com.ksilisk.telegram.bot.starter.handler.LongPollingHandler;
import com.ksilisk.telegram.bot.starter.handler.WebhookHandler;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import com.ksilisk.telegram.bot.starter.webhook.KafkaWebhookListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.annotation.Validated;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

@Configuration
@Slf4j
public class TelegramBotAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "telegram-bot", name = "webhook.enabled", havingValue = "false", matchIfMissing = true)
    @Profile({"dev", "prod"})
    public static class TelegramBotLongPollingConfiguration {
        @Bean
        @ConditionalOnMissingBean(TelegramBotsApi.class)
        public TelegramBotsApi longPollingBotsApi(List<LongPollingHandler> telegramBots) throws TelegramApiException {
            if (telegramBots.isEmpty()) {
                log.warn("No telegram-bot handlers available");
            }
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            for (LongPollingHandler bot : telegramBots) {
                log.info("Register telegram-bot long polling handler: {}", bot.getBotUsername());
                telegramBotsApi.registerBot(bot);
            }
            return telegramBotsApi;
        }

        @Bean
        @ConditionalOnMissingBean(LongPollingHandler.class)
        public LongPollingHandler longPollingHandler(BotProperties botProperties, Sender sender, UpdateProcessor updateProcessor) {
            return new LongPollingHandler(botProperties, updateProcessor, sender) {
                @Override
                protected void processUpdate(Update update) {
                    updateProcessor.process(update);
                }
            };
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "telegram-bot", name = "webhook.enabled", havingValue = "true")
    @Profile({"dev", "prod"})
    @EnableKafka
    public static class TelegramBotWebhookConfiguration {
        @Bean
        @ConfigurationProperties("telegram-bot.webhook")
        @Validated
        public WebhookProperties webhookProperties() {
            return new WebhookProperties();
        }

        @Bean
        @ConditionalOnMissingBean(Webhook.class)
        public Webhook webhook(WebhookHandler webhookHandler) {
            log.info("Register telegram-bot webhook handler: {}", webhookHandler);
            return new KafkaWebhookListener(webhookHandler);
        }

        @Bean
        @ConditionalOnMissingBean(WebhookHandler.class)
        public WebhookHandler webhookHandler(Sender sender, UpdateProcessor updateProcessor, BotProperties botProperties) {
            return new WebhookHandler(botProperties, updateProcessor, sender) {
                @Override
                protected void processUpdate(Update update) {
                    updateProcessor.process(update);
                }
            };
        }

        @Bean
        public TelegramBotsApi webhookBotsApi(Webhook webhook, List<WebhookBot> telegramBots,
                                              WebhookProperties properties) throws TelegramApiException {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
            SetWebhook setWebhook = buildSetWebhook(properties);
            for (WebhookBot bot : telegramBots) {
                telegramBotsApi.registerBot(bot, setWebhook);
            }
            return telegramBotsApi;
        }

        @Bean
        @ConditionalOnMissingBean(ContainerProperties.class)
        public ContainerProperties containerProperties(WebhookHandler webhookHandler, MessageListener<String, Update> kafkaListener) {
            ContainerProperties containerProperties = new ContainerProperties(webhookHandler.getBotPath());
            containerProperties.setMessageListener(kafkaListener);
            return containerProperties;
        }

        @Bean
        @ConditionalOnMissingBean(KafkaMessageListenerContainer.class)
        public KafkaMessageListenerContainer<String, Update> kafkaMessageListenerContainer(ConsumerFactory<String, Update> consumerFactory, ContainerProperties properties) {
            return new KafkaMessageListenerContainer<>(consumerFactory, properties);
        }

        @Bean
        @ConditionalOnMissingBean(ConsumerFactory.class)
        public ConsumerFactory<String, Update> consumerFactory(WebhookProperties webhookProperties) {
            Map<String, Object> props = new HashMap<>(webhookProperties.getConsumerConfigs());
            props.put(BOOTSTRAP_SERVERS_CONFIG, webhookProperties.getKafkaBrokers());
            props.put(GROUP_ID_CONFIG, webhookProperties.getConsumerGroup());
            return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Update.class));
        }

        private SetWebhook buildSetWebhook(WebhookProperties webhookProperties) {
            SetWebhook.SetWebhookBuilder builder = SetWebhook.builder();
            builder.url(webhookProperties.getUrl());
            File certificate = new File(webhookProperties.getCertificatePath());
            builder.certificate(new InputFile(certificate));
            builder.dropPendingUpdates(webhookProperties.isDropPendingUpdates());
            builder.maxConnections(webhookProperties.getMaxConnections());
            builder.ipAddress(webhookProperties.getIpAddress());
            if (webhookProperties.getSecretToken() != null) {
                builder.secretToken(webhookProperties.getSecretToken());
            }
            if (!webhookProperties.getAllowedUpdates().isEmpty()) {
                builder.allowedUpdates(webhookProperties.getAllowedUpdates());
            }
            return builder.build();
        }
    }

    @Bean
    @ConditionalOnMissingBean(Sender.class)
    public Sender sender(BotProperties botProperties) {
        return new Sender(botProperties.getToken()) {
        };
    }

    @Bean
    @ConfigurationProperties("telegram-bot")
    @Validated
    public BotProperties botProperties() {
        return new BotProperties();
    }
}
