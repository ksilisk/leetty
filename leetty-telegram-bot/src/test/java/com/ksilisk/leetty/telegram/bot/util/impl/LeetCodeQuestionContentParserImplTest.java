package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionContentParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

class LeetCodeQuestionContentParserImplTest {
    private static final String INPUT_PATH = "questions/input/";
    private static final String OUTPUT_DESCRIPTION_PATH = "questions/output/description/";
    private static final String OUTPUT_EXAMPLES_PATH = "questions/output/examples/";
    private static final String OUTPUT_CONSTRAINTS_PATH = "questions/output/constraints/";

    final LeetCodeQuestionContentParser contentParser = new LeetCodeQuestionContentParserImpl();

    @MethodSource("getAllInputFiles")
    @ParameterizedTest
    void prepareDescriptionTest(String inputFileName) throws URISyntaxException, IOException {
        // given
        String content = readClassPathFile(INPUT_PATH + inputFileName);
        String expectedDescription = readClassPathFile(OUTPUT_DESCRIPTION_PATH + inputFileName);
        // when
        String parsedDescription = contentParser.parseDescription(content);
        // then
        Assertions.assertEquals(expectedDescription, parsedDescription);
    }

    @MethodSource("getAllInputFiles")
    @ParameterizedTest
    void prepareExamplesTest(String inputFileName) throws URISyntaxException, IOException {
        // given
        String content = readClassPathFile(INPUT_PATH + inputFileName);
        String expectedExamples = readClassPathFile(OUTPUT_EXAMPLES_PATH + inputFileName);
        // when
        String parsedExamples = String.join("\n\n", contentParser.parseExamples(content));
        // then
        Assertions.assertEquals(expectedExamples, parsedExamples);
    }

    @MethodSource("getAllInputFiles")
    @ParameterizedTest
    void prepareConstraintsTest(String inputFileName) throws URISyntaxException, IOException {
        // given
        String content = readClassPathFile(INPUT_PATH + inputFileName);
        String expectedConstraints = readClassPathFile(OUTPUT_CONSTRAINTS_PATH + inputFileName);
        // when
        String parsedConstraints = contentParser.parseConstraints(content);
        // then
        Assertions.assertEquals(expectedConstraints, parsedConstraints);
    }

    static List<String> getAllInputFiles() throws URISyntaxException, IOException {
        Path inputDirPath = Paths.get(LeetCodeQuestionContentParser.class.getClassLoader().getResource(INPUT_PATH).toURI());
        try (Stream<Path> stream = Files.list(inputDirPath)) {
            return stream
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        }
    }

    private String readClassPathFile(String path) throws URISyntaxException, IOException {
        return Files.readString(Paths.get(this.getClass().getClassLoader().getResource(path).toURI()));
    }
}