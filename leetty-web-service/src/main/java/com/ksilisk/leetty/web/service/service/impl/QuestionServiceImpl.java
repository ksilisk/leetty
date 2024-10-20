package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.*;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.codegen.types.QuestionListFilterInput;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.common.question.*;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.exception.LeettyWebServiceException;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import com.ksilisk.leetty.web.service.service.QuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class QuestionServiceImpl implements QuestionService {
    private final GraphQLLeetCodeClient leetCodeClient;

    @Override
    public Question getQuestion(String titleSlug) {
        BaseSubProjectionNode<?, ?> questionProjectionNode = new QuestionProjectionRoot<>()
                .difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes().questionDetailUrl()
                .topicTags().name();
        return fetchQuestionProjection(titleSlug, questionProjectionNode);
    }

    @Override
    public Question getRandomQuestion(String categorySlug) {
        Question randomQuestion;
        do {
            GraphQLQuery randomQuestionQuery = RandomQuestionGraphQLQuery.newRequest()
                    .categorySlug(categorySlug)
                    .filters(QuestionListFilterInput.newBuilder().build())
                    .build();
            BaseSubProjectionNode<?, ?> randomQuestionProjectionNode = new RandomQuestionProjectionRoot<>()
                    .difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes().questionDetailUrl()
                    .topicTags().name();
            randomQuestion = leetCodeClient.execute(randomQuestionQuery, randomQuestionProjectionNode, Question.class)
                    .orElseThrow(LeettyWebServiceException::new);
            if (randomQuestion.getContent() == null) {
                log.info("Handled question without content. QuestionTitleSlug: {}. Retry request.", randomQuestion.getTitleSlug());
            }
        } while (randomQuestion.getContent() == null);
        return randomQuestion;
    }

    @Override
    public DailyCodingQuestion getDailyQuestions() {
        GraphQLQuery dailyQuestionQuery = ActiveDailyCodingChallengeQuestionGraphQLQuery.newRequest().build();
        BaseSubProjectionNode<?, ?> dailyQuestionProjectionNode = new ActiveDailyCodingChallengeQuestionProjectionRoot<>()
                .link()
                .question().difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes().hints()
                .topicTags().name();
        return leetCodeClient.execute(dailyQuestionQuery, dailyQuestionProjectionNode, DailyCodingQuestion.class)
                .orElseThrow(LeettyWebServiceException::new);
    }

    @Override
    public QuestionAcceptance getAcceptance(String titleSlug) {
        BaseSubProjectionNode<?, ?> acceptanceQuestionProjectionNode = new QuestionProjectionRoot<>().acRate();
        return new QuestionAcceptance(fetchQuestionProjection(titleSlug, acceptanceQuestionProjectionNode).getAcRate());
    }

    @Override
    public QuestionContent getContent(String titleSlug) {
        BaseSubProjectionNode<?, ?> contentProjection = new QuestionProjectionRoot<>().content();
        return new QuestionContent(fetchQuestionProjection(titleSlug, contentProjection).getContent());
    }

    @Override
    public QuestionHints getHints(String titleSlug) {
        BaseSubProjectionNode<?, ?> hintsProjection = new QuestionProjectionRoot<>().hints();
        return new QuestionHints(fetchQuestionProjection(titleSlug, hintsProjection).getHints());
    }

    @Override
    public QuestionTopics getTopics(String titleSlug) {
        BaseSubProjectionNode<?, ?> topicsProjection = new QuestionProjectionRoot<>().topicTags().name();
        List<TopicTag> topicTags = fetchQuestionProjection(titleSlug, topicsProjection).getTopicTags();
        return new QuestionTopics(topicTags.stream()
                .map(TopicTag::getName)
                .collect(Collectors.toList()));
    }

    @Override
    public QuestionDifficulty getDifficulty(String titleSlug) {
        BaseSubProjectionNode<?, ?> difficultyProjection = new QuestionProjectionRoot<>().difficulty();
        return new QuestionDifficulty(fetchQuestionProjection(titleSlug, difficultyProjection).getDifficulty());
    }


    private Question fetchQuestionProjection(String titleSlug, BaseSubProjectionNode<?, ?> questionProjectionNode) {
        GraphQLQuery questionQuery = QuestionGraphQLQuery.newRequest().titleSlug(titleSlug).build();
        return leetCodeClient.execute(questionQuery, questionProjectionNode, Question.class)
                .orElseThrow(() -> new QuestionNotFoundException("Question with titleSlug not found. TitleSlug: " + questionQuery));
    }
}
