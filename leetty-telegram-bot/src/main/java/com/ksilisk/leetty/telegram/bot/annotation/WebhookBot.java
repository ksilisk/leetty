package com.ksilisk.leetty.telegram.bot.annotation;

import com.ksilisk.leetty.telegram.bot.config.LeettyTelegramBotWebhookConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@ConditionalOnBean(LeettyTelegramBotWebhookConfiguration.class)
public @interface WebhookBot {
}
