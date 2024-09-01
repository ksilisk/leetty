package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.telegram.bot.keyboard.QuestionMessageInlineKeyboard;
import com.ksilisk.leetty.telegram.bot.service.LeetCodeQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionContentParser;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.List;

@Component
@RequiredArgsConstructor
class LeetCodeQuestionMessagePreparerImpl implements LeetCodeQuestionMessagePreparer {
    private static final String QUESTION_MESSAGE_HEADER_FORMAT = "%s. %s";
    private static final String INLINE_QUESTION_MESSAGE_SAMPLE_FILENAME = "question_format.txt";

    private final LeetCodeQuestionContentParser contentParser;
    private final MessageSampleReader sampleReader;

    @Override
    public SendMessage prepareMessage(Question leetCodeQuestion, Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(createMessageText(leetCodeQuestion))
                .disableWebPagePreview(true)
                .replyMarkup(QuestionMessageInlineKeyboard.forQuestion(leetCodeQuestion))
                .build();
    }

    @Override
    public void prepareInlineQueryResult(InlineQueryResultArticle.InlineQueryResultArticleBuilder articleBuilder,
                                         Question leetCodeQuestion) {
        articleBuilder.inputMessageContent(createMessageContent(leetCodeQuestion));
    }

    private InputTextMessageContent createMessageContent(Question leetCodeQuestion) {
        String messageSample = sampleReader.read(INLINE_QUESTION_MESSAGE_SAMPLE_FILENAME);
        return InputTextMessageContent.builder()
                .messageText(createInlineMessageText(leetCodeQuestion, messageSample))
                .disableWebPagePreview(true)
                .build();
    }

    private String createInlineMessageText(Question question, String messageSample) {
        List<String> topics = question.getTopicTags().stream().map(TopicTag::getName).toList();
        String description = contentParser.parseDescription(question.getContent());
        String examples = "Examples:\n\n" + contentParser.parseExamples(question.getContent());
        String constraints = "Constraints:\n\n" + contentParser.parseConstraints(question.getContent());
        return String.format(messageSample,
                question.getQuestionFrontendId(),
                question.getTitle(),
                String.join("\n\n", description, examples, constraints),
                question.getDifficulty(),
                String.join(", ", topics),
                question.getAcRate());
    }

    private String createMessageText(Question question) {
        String header =
                String.format(QUESTION_MESSAGE_HEADER_FORMAT, question.getQuestionFrontendId(), question.getTitle());
        return String.join("\n\n", header, contentParser.parseDescription(question.getContent()));
    }
}
