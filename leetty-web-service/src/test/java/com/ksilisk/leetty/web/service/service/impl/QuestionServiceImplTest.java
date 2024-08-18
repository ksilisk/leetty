package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.QuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.client.graphql.impl.GraphQLLeetCodeClientImpl;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import com.ksilisk.leetty.web.service.service.QuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionServiceImplTest {
    final GraphQLLeetCodeClient leetCodeClient = Mockito.mock(GraphQLLeetCodeClientImpl.class);
    final QuestionService questionService = new QuestionServiceImpl(leetCodeClient);

    @Test
    void getDailyQuestionTest_shouldNotThrowException() {
        // given
        DailyCodingQuestion expectedQuestion = new DailyCodingQuestion("date", "status", "link", null);
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(ActiveDailyCodingChallengeQuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(DailyCodingQuestion.class)))
                .thenReturn(expectedQuestion);
        // when
        DailyCodingQuestion actualQuestion = questionService.getDailyQuestions();
        // then
        assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    void getDoesNotExistQuestion_shouldThrowException() {
        // given
        String titleSlug = "test";
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(QuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(Question.class)))
                .thenReturn(null);
        // then
        assertThrows(QuestionNotFoundException.class, () -> questionService.getQuestion(titleSlug));
    }

    @Test
    void getExistQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        Question question = Question.newBuilder().titleSlug("two-sum").build();
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(QuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(Question.class)))
                .thenReturn(question);
        // when
        Question returnedQuestion = questionService.getQuestion(titleSlug);
        // then
        assertEquals(question, returnedQuestion);
    }
}