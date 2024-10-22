package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionContentParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
class LeetCodeQuestionContentParserImpl implements LeetCodeQuestionContentParser {
    private static final String ANY_HTML_TAG_REGEX = "<(([^>])*)>|</(([^>])*)>";
    private static final String EXAMPLES_SEPARATOR_REGEX = "<p><strong class=\"example\">Example .:</strong></p>";
    private static final String CONTENT_SEPARATOR = "<p>&nbsp;</p>";
    private static final String CONSTRAINTS_HEADER_REGEX = "Constraints:\n";

    @Override
    public List<String> parseExamples(String content) {
        String nonPreparedExamples = clearAndSeparateContent(content)[1];
        String[] splitExamples = nonPreparedExamples.split(EXAMPLES_SEPARATOR_REGEX);
        return Arrays.stream(splitExamples, 1, splitExamples.length) // start from 1, because first element is empty string
                .map(String::trim)
                .map(this::removeAndReplaceHTMLTags)
                .map(this::prepareExample)
                .collect(Collectors.toList());
    }

    private String prepareExample(String rawExample) {
        List<String> exampleLines = new ArrayList<>();
        String[] splitExample = rawExample.split("\n");
        for (String line : splitExample) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isBlank()) {
                exampleLines.add(trimmedLine);
            }
        }
        return String.join("\n", exampleLines);

    }

    @Override
    public String parseDescription(String content) {
        String nonPreparedDescription = clearAndSeparateContent(content)[0];
        nonPreparedDescription = removeAndReplaceHTMLTags(nonPreparedDescription).replaceAll("\n{2,}", "\n");
        String[] splitLines = nonPreparedDescription.split("\n");
        return Arrays.stream(splitLines)
                .map(String::trim)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String parseConstraints(String content) {
        String nonPreparedConstraints = clearAndSeparateContent(content)[2];
        nonPreparedConstraints = removeAndReplaceHTMLTags(nonPreparedConstraints)
                .replaceAll("\n{2,}", "\n").replaceAll(CONSTRAINTS_HEADER_REGEX, "");
        String[] splitLines = nonPreparedConstraints.split("\n");
        return Arrays.stream(splitLines, 1, splitLines.length) // start from 1, because first element is empty string
                .map(String::trim)
                .collect(Collectors.joining("\n"));
    }

    private String[] clearAndSeparateContent(String content) {
        String clearedContent = content.replaceAll("\n{2,}", "\n");
        return clearedContent.split(CONTENT_SEPARATOR);
    }

    private String removeAndReplaceHTMLTags(String nonClearedHTML) {
        return nonClearedHTML
                .replaceAll(ANY_HTML_TAG_REGEX, "")
                .replaceAll("<sup>", "^").replaceAll("&nbsp;", " ")
                .replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
                .replaceAll("&#39;", "'")
                .replaceAll("<sub>", "(").replaceAll("</sub>", ")");
    }
}
