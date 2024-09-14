package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.QuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.RandomQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.codegen.types.TopicTag;
import com.ksilisk.leetty.common.dto.question.*;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.client.graphql.impl.GraphQLLeetCodeClientImpl;
import com.ksilisk.leetty.web.service.exception.type.QuestionNotFoundException;
import com.ksilisk.leetty.web.service.service.QuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {
    final GraphQLLeetCodeClient leetCodeClient = Mockito.mock(GraphQLLeetCodeClientImpl.class);
    final QuestionService questionService = new QuestionServiceImpl(leetCodeClient);

    @Test
    void getRandomQuestionWithFirstResultContentIsNull_shouldRetryAndReturnQuestionWithContent() {
        // given
        Question withoutContent = Question.newBuilder()
                .title("Test Without Content")
                .titleSlug("test-without-content")
                .build();
        Question expectedQuestionWithContent = Question.newBuilder()
                .title("Test With Content")
                .titleSlug("test-with-content")
                .content("Test Content For Question")
                .build();
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(RandomQuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(Question.class)))
                .thenReturn(withoutContent, expectedQuestionWithContent);
        // when
        Question actualQuestion = questionService.getRandomQuestion("testSlug");
        // then
        assertEquals(expectedQuestionWithContent, actualQuestion);
    }

    @Test
    void getRandomQuestionTest_shouldGetRandomAndNotThrowException() {
        // given
        Question expectedRandomQuestion =
                Question.newBuilder().title("test").content("testContent").titleSlug("testSlug").build();
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(RandomQuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(Question.class)))
                .thenReturn(expectedRandomQuestion);
        // when
        Question actualRandomQuestion = questionService.getRandomQuestion("testSlug");
        // then
        assertEquals(expectedRandomQuestion, actualRandomQuestion);
    }

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
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getQuestion(titleSlug));
    }

    @Test
    void getExistQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        Question question = Question.newBuilder().titleSlug(titleSlug).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        Question returnedQuestion = questionService.getQuestion(titleSlug);
        // then
        assertEquals(question, returnedQuestion);
    }

    @Test
    void getDifficultyOfExistsQuestion_shouldReturn() {
        // given
        String difficulty = "Hard";
        String titleSlug = "two-sum";
        Question question = Question.newBuilder().titleSlug(titleSlug).difficulty(difficulty).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        QuestionDifficulty questionDifficulty = questionService.getDifficulty(titleSlug);
        // then
        assertEquals(difficulty, questionDifficulty.difficulty());
    }

    @Test
    void getTopicsOfExistsQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        List<TopicTag> topicTags = List.of(
                TopicTag.newBuilder().name("Topic1").build(),
                TopicTag.newBuilder().name("Topic2").build());
        Question question = Question.newBuilder().titleSlug(titleSlug).topicTags(topicTags).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        QuestionTopics questionTopics = questionService.getTopics(titleSlug);
        // then
        assertIterableEquals(topicTags.stream().map(TopicTag::getName).toList(), questionTopics.topics());
    }

    @Test
    void getHintsOfExistsQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        List<String> hints = List.of("Hint1", "Hint2");
        Question question = Question.newBuilder().titleSlug(titleSlug).hints(hints).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        QuestionHints questionHints = questionService.getHints(titleSlug);
        // then
        assertIterableEquals(hints, questionHints.hints());
    }

    @Test
    void getContentOfExistsQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        String content = "some super content";
        Question question = Question.newBuilder().titleSlug(titleSlug).content(content).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        QuestionContent questionContent = questionService.getContent(titleSlug);
        // then
        assertEquals(content, questionContent.content());
    }

    @Test
    void getAcceptanceOfExistsQuestion_shouldReturn() {
        // given
        String titleSlug = "two-sum";
        Double acceptance = 55.4532512534;
        Question question = Question.newBuilder().titleSlug(titleSlug).acRate(acceptance).build();
        setLeetCodeClientReturnQuestion(question);
        // when
        QuestionAcceptance questionAcceptance = questionService.getAcceptance(titleSlug);
        // then
        assertEquals(acceptance, questionAcceptance.acceptance());
    }

    @Test
    void getDifficultyNotExistsQuestion_shouldThrowException() {
        // given
        String titleSlug = "not-exist";
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getDifficulty(titleSlug));
    }

    @Test
    void getTopicsNotExistsQuestion_shouldThrowException() {
        // given
        String titleSlug = "not-exist";
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getTopics(titleSlug));
    }

    @Test
    void getHintsNotExistsQuestion_shouldThrowException() {
        // given
        String titleSlug = "not-exist";
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getHints(titleSlug));
    }

    @Test
    void getContentNotExistsQuestion_shouldThrowException() {
        // given
        String titleSlug = "not-exist";
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getContent(titleSlug));
    }

    @Test
    void getAcceptanceNotExistsQuestion_shouldThrowException() {
        // given
        String titleSlug = "not-exist";
        setLeetCodeClientReturnQuestion(null);
        // then
        assertThrowsExactly(QuestionNotFoundException.class, () -> questionService.getAcceptance(titleSlug));
    }

    private void setLeetCodeClientReturnQuestion(Question returnQuestion) {
        Mockito.when(leetCodeClient.execute(
                        ArgumentMatchers.isA(QuestionGraphQLQuery.class),
                        ArgumentMatchers.isA(BaseSubProjectionNode.class),
                        ArgumentMatchers.eq(Question.class)))
                .thenReturn(returnQuestion);
    }
}