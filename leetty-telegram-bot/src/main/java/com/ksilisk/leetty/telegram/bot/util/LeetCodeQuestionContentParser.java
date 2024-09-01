package com.ksilisk.leetty.telegram.bot.util;

import java.util.List;

public interface LeetCodeQuestionContentParser {
    List<String> parseExamples(String content);

    String parseDescription(String content);

    String parseConstraints(String content);
}
