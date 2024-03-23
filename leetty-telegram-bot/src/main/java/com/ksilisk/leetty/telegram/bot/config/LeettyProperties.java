package com.ksilisk.leetty.telegram.bot.config;

import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;

@Getter
@Setter
public class LeettyProperties {
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
