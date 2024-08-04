package com.ksilisk.leetty.telegram.bot.payload;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.*;

@Getter
@RequiredArgsConstructor
public enum LeettyBotCommand {
    START("/start", START_COMMAND),
    SETTINGS("/settings", SETTINGS_COMMAND),
    DAILY_QUESTION("/daily_question", DAILY_QUESTION_COMMAND),
    HELP("/help", HELP_COMMAND);

    private final String command;
    private final LeettyBotEvent event;
}
