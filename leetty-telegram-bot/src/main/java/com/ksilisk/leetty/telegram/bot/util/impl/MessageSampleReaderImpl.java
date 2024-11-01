package com.ksilisk.leetty.telegram.bot.util.impl;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.util.MessageSampleReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageSampleReaderImpl implements MessageSampleReader {
    private static final String PATH_SEPARATOR = "/";

    private final Map<String, String> cachedSamples = new ConcurrentHashMap<>();
    private final Map<String, Long> samplesModificationTimestamp = new ConcurrentHashMap<>();
    private final String messagesSamplesPath;

    public MessageSampleReaderImpl(LeettyProperties leettyProperties) {
        this.messagesSamplesPath = leettyProperties.getMessagesSamplesPath();
    }

    @Override
    public String read(String fileName) {
        return cachedSamples.compute(fileName, (key, value) -> {
            if (value == null || checkIsModificated(key)) {
                return readFromDisk(key);
            } else {
                return value;
            }
        });
    }

    private boolean checkIsModificated(String fileName) {
        try {
            File messageSampleFile = getFile(fileName);
            return samplesModificationTimestamp.get(fileName) < messageSampleFile.lastModified();
        } catch (FileNotFoundException e) {
            log.warn("Message sample not found. Maybe it has been deleted. Samples Path: {}, FileName: {}", messagesSamplesPath, fileName, e);
            throw new IllegalStateException(e);
        }
    }

    private String readFromDisk(String fileName) {
        try {
            File messageSampleFile = getFile(fileName);
            samplesModificationTimestamp.put(fileName, messageSampleFile.lastModified());
            return Files.readString(messageSampleFile.toPath());
        } catch (FileNotFoundException e) {
            log.warn("Message sample file not found. Samples Path: {}, FileName: {}", messagesSamplesPath, fileName, e);
            throw new IllegalStateException(e);
        } catch (IOException e) {
            log.warn("Error while reading message sample file. Samples Path: {}, FileName: {}", messagesSamplesPath, fileName, e);
            throw new IllegalStateException(e);
        }
    }

    private File getFile(String fileName) throws FileNotFoundException {
        return ResourceUtils.getFile(messagesSamplesPath + PATH_SEPARATOR + fileName);
    }
}
