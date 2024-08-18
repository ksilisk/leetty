package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.common.codegen.types.Question;

public interface QuestionService {
    Question getQuestion(String questionTitle);

    DailyCodingQuestion getDailyQuestions();
}
