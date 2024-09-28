package com.ksilisk.telegram.bot.starter.webhook;

public interface TelegramBotKafkaTopicConvention {
    String topicName(String botUsername);
}
