package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.common.codegen.types.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle.InlineQueryResultArticleBuilder;

public interface LeetCodeQuestionMessagePreparer {
    SendMessage prepareMessage(Question dailyCodingQuestion, Long chatId);

    void prepareInlineQueryResult(InlineQueryResultArticleBuilder articleBuilder, Question leetCodeQuestion);
}
