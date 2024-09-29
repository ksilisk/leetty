package com.ksilisk.leetty.telegram.bot.event;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum LeettyBotEvent {
    START_COMMAND,
    SETTINGS_COMMAND,
    PROFILE_COMMAND,
    DAILY_QUESTION_COMMAND,
    INLINE_QUERY_COMMAND,
    RANDOM_QUESTION_COMMAND,
    HELP_COMMAND,
    SET_LEETCODE_PROFILE,
    UPDATE_LEETCODE_PROFILE,
    SET_SEND_DAILY_TIME,
    UPDATE_SEND_DAILY_TIME,
    GET_QUESTION_ACCEPTANCE,
    GET_QUESTION_DIFFICULTY,
    GET_QUESTION_CONSTRAINTS,
    GET_QUESTION_EXAMPLES,
    GET_QUESTION_HINTS,
    GET_QUESTION_TOPICS,
    MESSAGE,
    INVALID;

    private static final Map<String, LeettyBotEvent> EVENTS_MAP =
            Arrays.stream(LeettyBotEvent.values())
                    .collect(Collectors.toMap(Enum::toString, Function.identity()));

    public static LeettyBotEvent resolve(String currentEvent) {
        return EVENTS_MAP.getOrDefault(currentEvent, INVALID);
    }
}
