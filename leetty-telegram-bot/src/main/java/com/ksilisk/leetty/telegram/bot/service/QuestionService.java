package com.ksilisk.leetty.telegram.bot.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;
import com.ksilisk.leetty.common.dto.question.*;

public interface QuestionService {
    DailyCodingQuestion getDailyQuestion();

    Question getRandomQuestion();

    Question parseQuestionFromUrl(String titleSlug);

    QuestionAcceptance getAcceptance(String titleSlug);

    QuestionContent getContent(String titleSlug);

    QuestionDifficulty getDifficulty(String titleSlug);

    QuestionTopics getTopics(String titleSlug);

    QuestionHints getHints(String titleSlug);
}
