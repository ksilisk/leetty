package com.ksilisk.leetty.telegram.bot.action.impl;

import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.telegram.bot.action.LeettyAction;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties.InlineModeProperties;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties.InlineModeProperties.ResultMessageProperties;
import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.service.LeetCodeQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery.AnswerInlineQueryBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle.InlineQueryResultArticleBuilder;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InlineQueryCommandAction extends LeettyAction {
    private static final String INLINE_QUERY_HELP_MESSAGE_FILENAME = "inline_query_help_message.txt";

    private final Sender sender;
    private final MessageSampleReader messageSampleReader;
    private final QuestionService questionService;
    private final LeetCodeQuestionMessagePreparer messagePreparer;
    private final InlineModeProperties inlineModeProperties;

    @Override
    public void handle(Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();
        String leetCodeUrl = inlineQuery.getQuery();
        AnswerInlineQueryBuilder answerInlineQuery = AnswerInlineQuery.builder()
                .inlineQueryId(inlineQuery.getId());
        try {
            Question leetCodeQuestion = questionService.parseQuestionFromUrl(leetCodeUrl);
            answerInlineQuery.result(createQuestionMessageQuery(leetCodeQuestion));
        } catch (Exception ex) {
            log.warn("Error while process inline query. Sending help result. Update: {}", update, ex);
            answerInlineQuery.result(createHelpMessageQuery());
            answerInlineQuery.result(createRandomQuestionMessageQuery());
        }
        sender.execute(answerInlineQuery.build());
    }

    private InlineQueryResultArticle createQuestionMessageQuery(Question question) {
        ResultMessageProperties resultMessageProperties = inlineModeProperties.getResultMessage();
        InlineQueryResultArticleBuilder queryResultBuilder = InlineQueryResultArticle.builder()
                .id(UUID.randomUUID().toString())
                .title(question.getTitle());
        messagePreparer.prepareInlineQueryResult(queryResultBuilder, question);
        setThumbnail(queryResultBuilder, resultMessageProperties.getThumbnail());
        return queryResultBuilder.build();
    }

    private InlineQueryResultArticle createRandomQuestionMessageQuery() {
        InlineModeProperties.RandomQuestionMessageProperties randomQuestionMessageProperties = inlineModeProperties.getRandomMessage();
        Question randomQuestion = questionService.getRandomQuestion();
        InlineQueryResultArticle.InlineQueryResultArticleBuilder randomQuestionQueryBuilder = InlineQueryResultArticle.builder()
                .id(UUID.randomUUID().toString())
                .title(randomQuestionMessageProperties.getTitle());
        messagePreparer.prepareInlineQueryResult(randomQuestionQueryBuilder, randomQuestion);
        if (StringUtils.hasText(randomQuestionMessageProperties.getDescription())) {
            randomQuestionQueryBuilder.description(randomQuestionMessageProperties.getDescription());
        }
        setThumbnail(randomQuestionQueryBuilder, randomQuestionMessageProperties.getThumbnail());
        return randomQuestionQueryBuilder.build();
    }

    private InlineQueryResultArticle createHelpMessageQuery() {
        InlineModeProperties.HelpMessageProperties helpMessageProperties = inlineModeProperties.getHelpMessage();
        String helpMessage = messageSampleReader.read(INLINE_QUERY_HELP_MESSAGE_FILENAME);
        InputTextMessageContent inputTextMessageContent = InputTextMessageContent.builder()
                .messageText(helpMessage)
                .disableWebPagePreview(true)
                .build();
        InlineQueryResultArticleBuilder queryResultBuilder = InlineQueryResultArticle.builder()
                .id(UUID.randomUUID().toString())
                .title(helpMessageProperties.getTitle())
                .inputMessageContent(inputTextMessageContent);
        if (StringUtils.hasText(helpMessageProperties.getDescription())) {
            queryResultBuilder.description(helpMessageProperties.getDescription());
        }
        setThumbnail(queryResultBuilder, helpMessageProperties.getThumbnail());
        return queryResultBuilder.build();
    }

    private void setThumbnail(InlineQueryResultArticleBuilder queryResultBuilder, InlineModeProperties.ThumbnailProperties properties) {
        if (StringUtils.hasText(properties.getUrl())) {
            queryResultBuilder.thumbnailUrl(properties.getUrl());
        }
        if (properties.getHeight() != null) {
            queryResultBuilder.thumbnailHeight(properties.getHeight());
        }
        if (properties.getWidth() != null) {
            queryResultBuilder.thumbnailWidth(properties.getWidth());
        }
    }

    @Override
    public LeettyBotEvent getEvent() {
        return LeettyBotEvent.INLINE_QUERY_COMMAND;
    }
}
