package com.ksilisk.leetty.telegram.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.ZoneId;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("leetty")
public class LeettyProperties {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final String DEFAULT_MESSAGES_SAMPLES_PATH = "classpath:messages";

    private ZoneId zoneId = DEFAULT_ZONE_ID;
    private String messagesSamplesPath = DEFAULT_MESSAGES_SAMPLES_PATH;
}
