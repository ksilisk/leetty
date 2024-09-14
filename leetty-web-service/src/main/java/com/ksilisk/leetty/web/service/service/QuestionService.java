package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.dto.question.*;

public interface QuestionService {
    Question getQuestion(String titleSlug);

    Question getRandomQuestion(String categorySlug);

    DailyCodingQuestion getDailyQuestions();

    QuestionAcceptance getAcceptance(String titleSlug);

    QuestionContent getContent(String titleSlug);

    QuestionHints getHints(String titleSlug);

    QuestionTopics getTopics(String titleSlug);

    QuestionDifficulty getDifficulty(String titleSlug);
}
