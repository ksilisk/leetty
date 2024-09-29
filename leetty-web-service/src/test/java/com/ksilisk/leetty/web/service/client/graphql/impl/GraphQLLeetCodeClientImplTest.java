package com.ksilisk.leetty.web.service.client.graphql.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionProjectionRoot;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.RestClientGraphQLClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

class GraphQLLeetCodeClientImplTest {
    private final ObjectMapper om = new ObjectMapper();

    @Test
    void executeTest_shouldNotThrowException() throws JsonProcessingException {
        // given
        DailyCodingQuestion question = DailyCodingQuestion.newBuilder().userStatus("super").date("date").link("superLink").build();
        String testJson = "{\"data\": {\"activeDailyCodingChallengeQuestion\":" + om.writeValueAsString(question) + "}}";
        RestClientGraphQLClient restClient = Mockito.mock(RestClientGraphQLClient.class);
        GraphQLLeetCodeClient leetCodeClient = new GraphQLLeetCodeClientImpl(restClient);
        GraphQLResponse response = new GraphQLResponse(testJson);
        Mockito.when(restClient.executeQuery(ArgumentMatchers.anyString())).then((Answer<GraphQLResponse>) invocation -> response);
        // when
        DailyCodingQuestion executeResult = leetCodeClient.execute(ActiveDailyCodingChallengeQuestionGraphQLQuery.newRequest().build(),
                new ActiveDailyCodingChallengeQuestionProjectionRoot<>().date().userStatus().link(), DailyCodingQuestion.class).get();
        // then
        Assertions.assertEquals(question, executeResult);
    }
}