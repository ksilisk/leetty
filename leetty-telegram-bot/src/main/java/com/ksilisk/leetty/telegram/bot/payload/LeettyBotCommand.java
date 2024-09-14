package com.ksilisk.leetty.telegram.bot.payload;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.*;

@Getter
@RequiredArgsConstructor
public enum LeettyBotCommand {
    START("/start", START_COMMAND),
    SETTINGS("/settings", SETTINGS_COMMAND),
    DAILY_QUESTION("/daily_question", DAILY_QUESTION_COMMAND),
    RANDOM_QUESTION("/random_question", RANDOM_QUESTION_COMMAND),
    HELP("/help", HELP_COMMAND);

    private static final Map<String, LeettyBotCommand> COMMANDS_MAP =
            Arrays.stream(LeettyBotCommand.values())
                    .collect(Collectors.toMap(LeettyBotCommand::getCommand, Function.identity()));

    public static LeettyBotEvent resolve(String givenCommand) {
        if (COMMANDS_MAP.containsKey(givenCommand)) {
            return COMMANDS_MAP.get(givenCommand).getEvent();
        }
        return INVALID;
    }

    private final String command;
    private final LeettyBotEvent event;
}
