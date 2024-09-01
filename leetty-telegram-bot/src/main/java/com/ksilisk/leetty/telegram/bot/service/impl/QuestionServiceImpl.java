package com.ksilisk.leetty.telegram.bot.service.impl;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.dto.question.*;
import com.ksilisk.leetty.telegram.bot.feign.QuestionClient;
import com.ksilisk.leetty.telegram.bot.service.QuestionService;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeUrlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class QuestionServiceImpl implements QuestionService {
    private final LeetCodeUrlParser leetCodeUrlParser;
    private final QuestionClient questionClient;

    @Override
    public DailyCodingQuestion getDailyQuestion() {
        return questionClient.getDailyQuestion();
    }

    @Override
    public Question parseQuestionFromUrl(String url) {
        String titleSlug = leetCodeUrlParser.getTitleSlug(url);
        return questionClient.getLeetCodeQuestion(titleSlug);
    }

    @Override
    public QuestionAcceptance getAcceptance(String titleSlug) {
        return questionClient.getAcceptance(titleSlug);
    }

    @Override
    public QuestionContent getContent(String titleSlug) {
        return questionClient.getContent(titleSlug);
    }

    @Override
    public QuestionDifficulty getDifficulty(String titleSlug) {
        return questionClient.getDifficulty(titleSlug);
    }

    @Override
    public QuestionTopics getTopics(String titleSlug) {
        return questionClient.getTopics(titleSlug);
    }

    @Override
    public QuestionHints getHints(String titleSlug) {
        return questionClient.getHints(titleSlug);
    }
}
