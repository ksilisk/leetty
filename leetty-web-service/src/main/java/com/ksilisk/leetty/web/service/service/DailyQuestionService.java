package com.ksilisk.leetty.web.service.service;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;

import java.util.List;

public interface DailyQuestionService {
    DailyCodingQuestion getQuestion();

    List<Long> getChatsToSendDaily(String time);
}
