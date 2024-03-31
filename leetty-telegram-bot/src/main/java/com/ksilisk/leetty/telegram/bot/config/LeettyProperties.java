package com.ksilisk.leetty.telegram.bot.config;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.util.EnumMap;

@Getter
@Setter
public class LeettyProperties {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final String DEFAULT_MESSAGES_SAMPLES_PATH = "classpath:messages";

    private ZoneId zoneId = DEFAULT_ZONE_ID;
    private String messagesSamplesPath = DEFAULT_MESSAGES_SAMPLES_PATH;
    private EnumMap<Bot, BotProperties> botProperties;

    @Getter
    @Setter
    public static class BotProperties {
        private String token;
        private String username;
    }

    public enum Bot {
        LEETTY
    }
}
