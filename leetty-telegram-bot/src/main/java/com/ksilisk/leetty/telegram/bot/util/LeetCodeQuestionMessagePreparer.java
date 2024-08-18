package com.ksilisk.leetty.telegram.bot.util;

import com.ksilisk.leetty.common.codegen.types.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;

public interface LeetCodeQuestionMessagePreparer {
    SendMessage prepareMessage(Question dailyCodingQuestion, Long chatId);

    InputTextMessageContent prepareMessageContent(Question question);
}
