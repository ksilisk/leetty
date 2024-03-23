package com.ksilisk.leetty.web.service.service.impl;

import com.ksilisk.leetty.common.codegen.client.ActiveDailyCodingChallengeQuestionGraphQLQuery;
import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.web.service.client.graphql.GraphQLLeetCodeClient;
import com.ksilisk.leetty.web.service.client.graphql.impl.GraphQLLeetCodeClientImpl;
import com.ksilisk.leetty.web.service.entity.Chat;
import com.ksilisk.leetty.web.service.repository.ChatRepository;
import com.ksilisk.leetty.web.service.service.DailyQuestionService;
import com.netflix.graphql.dgs.client.codegen.BaseSubProjectionNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@DataJpaTest
class DailyQuestionServiceImplTest {
    final GraphQLLeetCodeClient leetCodeClient = Mockito.mock(GraphQLLeetCodeClientImpl.class);

    final ChatRepository chatRepository;
    final DailyQuestionService dailyQuestionService;

    @Autowired
    public DailyQuestionServiceImplTest(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        this.dailyQuestionService = new DailyQuestionServiceImpl(leetCodeClient, chatRepository);
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
        DailyCodingQuestion actualQuestion = dailyQuestionService.getQuestion();
        // then
        Assertions.assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    void getChatsToSendDaily_whenChatsEmpty_shouldReturnEmptyList() {
        // given
        List<Long> expectedChatList = Collections.emptyList();
        // when
        List<Long> actualChatList = dailyQuestionService.getChatsToSendDaily("18:00");
        // then
        Assertions.assertEquals(expectedChatList, actualChatList);
    }

    @Test
    void getChatsToSendDaily_whenChatsNotEmpty_shouldReturnEmptyList() {
        // given
        List<Chat> expectedChats = List.of(
                Chat.builder().chatId(1L).dailySendTime("10:00").build(),
                Chat.builder().chatId(2L).dailySendTime("10:00").build(),
                Chat.builder().chatId(4L).dailySendTime("10:00").build());
        List<Chat> allChats = new ArrayList<>(expectedChats);
        allChats.addAll(List.of(
                Chat.builder().chatId(3L).dailySendTime("15:00").build(),
                Chat.builder().chatId(5L).dailySendTime("16:00").build()));
        List<Long> expectedIds = expectedChats.stream().map(Chat::getChatId).toList();
        // when
        chatRepository.saveAll(allChats);
        List<Long> actualIds = dailyQuestionService.getChatsToSendDaily("10:00");
        // then
        Assertions.assertIterableEquals(expectedIds, actualIds);
    }
}