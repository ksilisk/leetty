package com.ksilisk.leetty.telegram.bot.util;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface DailyQuestionMessagePreparer {
    SendMessage prepareMessage(DailyCodingQuestion dailyCodingQuestion, Long chatId);
}
