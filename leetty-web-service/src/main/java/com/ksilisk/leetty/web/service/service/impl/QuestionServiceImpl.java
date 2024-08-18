package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.*;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import com.ksilisk.leetty.web.service.service.QuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final GraphQLLeetCodeClient leetCodeClient;

    @Override
    public Question getQuestion(String titleSlug) {
        GraphQLQuery questionQuery = QuestionGraphQLQuery.newRequest()
                .queryName("question")
                .titleSlug(titleSlug)
                .build();
        BaseSubProjectionNode<?, ?> questionProjectionNode = new QuestionProjectionRoot<>()
                .difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes()
                .topicTags().name();
        Question question = leetCodeClient.execute(questionQuery, questionProjectionNode, Question.class);
        if (question == null) {
            throw new QuestionNotFoundException("Question with titleSlug not found. TitleSlug: " + titleSlug);
        }
        return question;
    }

    @Override
    public DailyCodingQuestion getDailyQuestions() {
        GraphQLQuery dailyQuestionQuery = ActiveDailyCodingChallengeQuestionGraphQLQuery.newRequest().build();
        BaseSubProjectionNode<?, ?> dailyQuestionProjectionNode = new ActiveDailyCodingChallengeQuestionProjectionRoot<>()
                .link()
                .question().difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes()
                .topicTags().name();
        return leetCodeClient.execute(dailyQuestionQuery, dailyQuestionProjectionNode, DailyCodingQuestion.class);
    }
}
