package com.ksilisk.telegram.bot.starter.webhook;

public class DefaultTelegramBotKafkaTopicConvention implements TelegramBotKafkaTopicConvention {
    @Override
    public String topicName(String botUsername) {
        return botUsername.toLowerCase().replaceAll("_", "-");
    }
}
