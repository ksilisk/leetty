package com.ksilisk.leetty.telegram.bot.config;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.util.EnumMap;

@Getter
@Setter
public class LeettyProperties {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Moscow");

    private ZoneId zoneId = DEFAULT_ZONE_ID;
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
