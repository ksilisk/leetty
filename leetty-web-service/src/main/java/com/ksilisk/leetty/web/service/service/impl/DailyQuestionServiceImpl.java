package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionProjectionRoot;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.service.DailyQuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyQuestionServiceImpl implements DailyQuestionService {
    private final GraphQLQuery graphQLQuery = ActiveDailyCodingChallengeQuestionGraphQLQuery.newRequest().build();
    private final GraphQLLeetCodeClient leetCodeClient;

    @Override
    public DailyCodingQuestion getQuestion() {
        BaseSubProjectionNode<?, ?> projectionNode = new ActiveDailyCodingChallengeQuestionProjectionRoot<>()
                .link()
                .question().difficulty().title().content().questionFrontendId().acRate().titleSlug().likes().dislikes()
                .topicTags().name();
        return leetCodeClient.execute(graphQLQuery, projectionNode, DailyCodingQuestion.class);
    }
}
