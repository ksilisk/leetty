package com.ksilisk.leetty.telegram.bot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum LeettyBotEvent {
    START_COMMAND(false),
    SETTINGS_COMMAND(false),
    PROFILE_COMMAND(false),
    DAILY_QUESTION_COMMAND(false),
    INLINE_QUERY_COMMAND(false),
    RANDOM_QUESTION_COMMAND(false),
    HELP_COMMAND(false),
    SET_LEETCODE_PROFILE(true),
    SET_SEND_DAILY_TIME(true),
    UPDATE_SEND_DAILY_TIME(true),
    GET_QUESTION_ACCEPTANCE(true),
    GET_QUESTION_DIFFICULTY(true),
    GET_QUESTION_CONSTRAINTS(true),
    GET_QUESTION_EXAMPLES(true),
    GET_QUESTION_HINTS(true),
    GET_QUESTION_TOPICS(true),
    MESSAGE(false),
    INVALID(false);

    private static final Map<String, LeettyBotEvent> EVENTS_MAP =
            Arrays.stream(LeettyBotEvent.values())
                    .collect(Collectors.toMap(Enum::toString, Function.identity()));

    public static LeettyBotEvent resolve(String currentEvent) {
        return EVENTS_MAP.getOrDefault(currentEvent, INVALID);
    }

    private final boolean isCallback;
}
