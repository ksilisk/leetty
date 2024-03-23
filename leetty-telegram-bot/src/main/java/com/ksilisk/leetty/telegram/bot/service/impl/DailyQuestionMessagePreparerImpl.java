package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.telegram.bot.service.DailyQuestionMessagePreparer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyQuestionMessagePreparerImpl implements DailyQuestionMessagePreparer {
    private final String messageFormat;

    public DailyQuestionMessagePreparerImpl(
            @Value("classpath:messages/daily_question_format.txt") Resource messageFormatResource) throws IOException {
        this.messageFormat = messageFormatResource.getContentAsString(StandardCharsets.UTF_8);
    }

    public SendMessage prepareMessage(DailyCodingQuestion dailyCodingQuestion, Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(prepareDailyQuestionText(dailyCodingQuestion))
                .disableWebPagePreview(true)
                .parseMode("HTML")
                .build();
    }

    private String prepareDailyQuestionText(DailyCodingQuestion dailyCodingQuestion) {
        return String.format(messageFormat,
                dailyCodingQuestion.getQuestion().getQuestionFrontendId(),
                dailyCodingQuestion.getLink(),
                dailyCodingQuestion.getQuestion().getTitle(),
                prepareQuestion(dailyCodingQuestion.getQuestion().getContent()),
                dailyCodingQuestion.getQuestion().getDifficulty(),
                prepareTopics(dailyCodingQuestion.getQuestion().getTopicTags()),
                dailyCodingQuestion.getQuestion().getAcRate());
    }

    private String prepareTopics(List<TopicTag> topicTags) {
        return topicTags.stream()
                .map(TopicTag::getName)
                .collect(Collectors.joining(", "));
    }

    private String prepareQuestion(String question) {
        return question.replaceAll("<p>", "")
                .replaceAll("</p>", "")
                .replaceAll("<ul>", "")
                .replaceAll("</ul>", "")
                .replaceAll("<li>", "")
                .replaceAll("</li>", "")
                .replaceAll("<sup>", "^")
                .replaceAll("</sup>", "")
                .replaceAll("&nbsp;", "")
                .replaceAll("<pre>", "")
                .replaceAll("</pre>", "")
                .replaceAll("\\t", "")
                .replaceAll("<sub>", "(")
                .replaceAll("</sub>", ")")
                .replaceAll("<div(([^>])*)>", "")
                .replaceAll("<span(([^>])*)>", "")
                .replaceAll("<img(([^>])*)/>", "")
                .replaceAll("</span>", "")
                .replaceAll("</div>", "")
                .replaceAll("[\\r\\n]{3,}", "\n\n");
    }

}
