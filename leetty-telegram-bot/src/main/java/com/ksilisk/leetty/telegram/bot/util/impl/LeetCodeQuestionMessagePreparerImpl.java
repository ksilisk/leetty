package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LeetCodeQuestionMessagePreparerImpl implements LeetCodeQuestionMessagePreparer {
    private static final String MESSAGE_FORMAT_SAMPLE_FILENAME = "daily_question_format.txt";
    private static final String LEETCODE_QUESTION_URL_FORMAT = "https://leetcode.com/problems/%s/description/";

    private final MessageSampleReader messageSampleReader;

    public SendMessage prepareMessage(Question leetCodeQuestion, Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(prepareQuestionText(leetCodeQuestion))
                .disableWebPagePreview(true)
                .parseMode("HTML")
                .build();
    }

    @Override
    public InputTextMessageContent prepareMessageContent(Question leetCodeQuestion) {
        return InputTextMessageContent.builder()
                .messageText(prepareQuestionText(leetCodeQuestion))
                .parseMode("HTML")
                .disableWebPagePreview(true)
                .build();
    }

    private String prepareQuestionText(Question leetCodeQuestion) {
        return String.format(messageSampleReader.read(MESSAGE_FORMAT_SAMPLE_FILENAME),
                leetCodeQuestion.getQuestionFrontendId(),
                String.format(LEETCODE_QUESTION_URL_FORMAT, leetCodeQuestion.getTitleSlug()),
                leetCodeQuestion.getTitle(),
                prepareQuestion(leetCodeQuestion.getContent()),
                leetCodeQuestion.getDifficulty(),
                prepareTopics(leetCodeQuestion.getTopicTags()),
                leetCodeQuestion.getAcRate());
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
                .replaceAll("</ol>", "")
                .replaceAll("<ol>", "")
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
