package com.ksilisk.leetty.telegram.bot.event;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum LeettyBotEvent {
    START_COMMAND,
    SETTINGS_COMMAND,
    DAILY_QUESTION_COMMAND,
    INLINE_QUERY_COMMAND,
    HELP_COMMAND,
    UPDATE_SEND_DAILY_TIME,
    SET_SEND_DAILY_TIME,
    MESSAGE,
    INVALID;

    private static final Map<String, LeettyBotEvent> EVENTS_MAP =
            Arrays.stream(LeettyBotEvent.values())
                    .collect(Collectors.toMap(Enum::toString, Function.identity()));

    public static LeettyBotEvent resolve(String currentEvent) {
        return EVENTS_MAP.getOrDefault(currentEvent, INVALID);
    }
}
