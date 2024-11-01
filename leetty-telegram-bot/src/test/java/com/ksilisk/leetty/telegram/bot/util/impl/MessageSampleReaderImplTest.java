package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class MessageSampleReaderImplTest {
    private static final String TEST_EXISTS_FILE_NAME = "test_sample_message.txt";

    @TempDir
    Path tempDir;

    @Test
    void readExistsSampleFromClasspathResources_shouldReadAndNotThrowException() {
        // given
        MessageSampleReader messageSampleReader = new MessageSampleReaderImpl(new LeettyProperties());
        String expectedString = "Test message sample!";
        // then
        Assertions.assertDoesNotThrow(() -> messageSampleReader.read(TEST_EXISTS_FILE_NAME));
        Assertions.assertEquals(expectedString, messageSampleReader.read(TEST_EXISTS_FILE_NAME));
    }

    @Test
    void readExistsSampleFromPath_shouldReadAndNotThrowException() throws IOException {
        // given
        LeettyProperties leettyProperties = new LeettyProperties();
        leettyProperties.setMessagesSamplesPath(tempDir.toString());
        MessageSampleReader messageSampleReader = new MessageSampleReaderImpl(leettyProperties);
        String expectedString = "Test message sample!";
        Path resolve = tempDir.resolve(TEST_EXISTS_FILE_NAME);
        Files.write(resolve, expectedString.getBytes());
        // then
        Assertions.assertDoesNotThrow(() -> messageSampleReader.read(TEST_EXISTS_FILE_NAME));
        Assertions.assertEquals(expectedString, messageSampleReader.read(TEST_EXISTS_FILE_NAME));
    }

    @Test
    void readNotExistsFileFromClasspath_shouldThrowException() {
        // given
        LeettyProperties leettyProperties = new LeettyProperties();
        MessageSampleReader messageSampleReader = new MessageSampleReaderImpl(leettyProperties);
        // then
        Assertions.assertThrowsExactly(IllegalStateException.class, () -> messageSampleReader.read("some_file.txt"));
    }

    @Test
    void readNotExistsFileFromPath_shouldThrowException() {
        // given
        LeettyProperties leettyProperties = new LeettyProperties();
        leettyProperties.setMessagesSamplesPath(tempDir.toString());
        MessageSampleReader messageSampleReader = new MessageSampleReaderImpl(leettyProperties);
        // then
        Assertions.assertThrowsExactly(IllegalStateException.class, () -> messageSampleReader.read("some_file.txt"));
    }

    @Test
    void readModificatedFile_shouldRereadFile() throws IOException {
        // given
        LeettyProperties leettyProperties = new LeettyProperties();
        leettyProperties.setMessagesSamplesPath(tempDir.toString());
        MessageSampleReader messageSampleReader = new MessageSampleReaderImpl(leettyProperties);
        String firstReadExpectedString = "Test message sample!";
        String secondReadExpectedString = "Second Test message sample!";
        Path resolve = tempDir.resolve(TEST_EXISTS_FILE_NAME);
        Files.write(resolve, firstReadExpectedString.getBytes());
        // when
        String firstReadSample = messageSampleReader.read(TEST_EXISTS_FILE_NAME);
        Files.write(resolve, secondReadExpectedString.getBytes());
        String secondReadSample = messageSampleReader.read(TEST_EXISTS_FILE_NAME);
        // then
        Assertions.assertEquals(firstReadExpectedString, firstReadSample);
        Assertions.assertEquals(secondReadExpectedString, secondReadSample);
    }
}