package com.ksilisk.leetty.telegram.bot.scheduled;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import com.ksilisk.leetty.telegram.bot.service.LeettyFacade;
import com.ksilisk.leetty.telegram.bot.util.LeetCodeQuestionMessagePreparer;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ksilisk.leetty.telegram.bot.action.impl.UpdateSendDailyTimeAction.TIMES_TO_SEND;

@Slf4j
@Component
public class ScheduledSendDailyQuestion implements Closeable {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final ZoneId zoneId;
    private final LeettyFacade leettyFacade;
    private final Sender sender;
    private final LeetCodeQuestionMessagePreparer leetCodeQuestionMessagePreparer;

    public ScheduledSendDailyQuestion(LeettyFacade leettyFacade, LeettyProperties leettyProperties,
                                      Sender sender, LeetCodeQuestionMessagePreparer leetCodeQuestionMessagePreparer) {
        this.leettyFacade = leettyFacade;
        this.sender = sender;
        this.leetCodeQuestionMessagePreparer = leetCodeQuestionMessagePreparer;
        this.zoneId = leettyProperties.getZoneId();
    }

    @PostConstruct
    public void addTimesToSchedule() {
        TIMES_TO_SEND.forEach(time -> {
            LocalTime now = LocalTime.now(zoneId);
            Duration nextRun = Duration.between(now, time);
            if (time.isBefore(now)) {
                nextRun = nextRun.plusDays(1);
            }
            executorService.scheduleAtFixedRate(() -> sendAll(time),
                    nextRun.toSeconds(),
                    TimeUnit.DAYS.toSeconds(1),
                    TimeUnit.SECONDS);
        });
    }

    private void sendAll(LocalTime time) {
        log.info("Start sending daily questions!");
        try {
            DailyCodingQuestion dailyCodingQuestion = leettyFacade.getDailyQuestion();
            leettyFacade.getUsersToSendDailyQuestion(time).stream()
                    .map(id -> leetCodeQuestionMessagePreparer.prepareMessage(dailyCodingQuestion.getQuestion(), id))
                    .forEach(sender::execute);
        } catch (Exception e) {
            log.warn("Error while scheduled send leetcode daily question.", e);
        }
    }

    @Override
    public void close() {
        try {
            executorService.shutdown();
        } catch (Exception e) {
            log.warn("Error while gracefully shutdown executor service. Hard shutdown now.", e);
            executorService.shutdownNow();
        }
    }
}
