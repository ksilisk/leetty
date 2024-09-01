package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionProjectionRoot;
import com.ksilisk.leetty.common.codegen.client.QuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.QuestionProjectionRoot;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.common.dto.question.*;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import com.ksilisk.leetty.web.service.service.QuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public DailyCodingQuestion getDailyQuestions() {
        GraphQLQuery dailyQuestionQuery = ActiveDailyCodingChallengeQuestionGraphQLQuery.newRequest().build();
        BaseSubProjectionNode<?, ?> dailyQuestionProjectionNode = new ActiveDailyCodingChallengeQuestionProjectionRoot<>()
                .link()
                .question().difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes().hints()
                .topicTags().name();
        return leetCodeClient.execute(dailyQuestionQuery, dailyQuestionProjectionNode, DailyCodingQuestion.class);
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
        Question question = leetCodeClient.execute(questionQuery, questionProjectionNode, Question.class);
        if (question == null) {
            throw new QuestionNotFoundException("Question with titleSlug not found. TitleSlug: " + questionQuery);
        }
        return question;
    }
}
