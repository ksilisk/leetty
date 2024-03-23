package com.ksilisk.leetty.telegram.bot.scheduled;

import com.ksilisk.leetty.common.codegen.types.DailyCodingQuestion;
import com.ksilisk.leetty.telegram.bot.sender.Sender;
import com.ksilisk.leetty.telegram.bot.sender.SenderResolver;
import com.ksilisk.leetty.telegram.bot.service.DailyQuestionMessagePreparer;
import com.ksilisk.leetty.telegram.bot.service.LeettyBotService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ksilisk.leetty.telegram.bot.action.leetty.impl.UpdateSendDailyTimeAction.TIMES_TO_SEND;
import static com.ksilisk.leetty.telegram.bot.config.LeettyProperties.Bot.LEETTY;

@Slf4j
@Component
public class ScheduledSendDailyQuestion implements Closeable {
    private final ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();

    private final LeettyBotService leettyBotService;
    private final Sender sender;
    private final DailyQuestionMessagePreparer dailyQuestionMessagePreparer;

    public ScheduledSendDailyQuestion(LeettyBotService leettyBotService, SenderResolver senderResolver, DailyQuestionMessagePreparer dailyQuestionMessagePreparer) {
        this.leettyBotService = leettyBotService;
        this.sender = senderResolver.getSender(LEETTY);
        this.dailyQuestionMessagePreparer = dailyQuestionMessagePreparer;
    }

    @PostConstruct
    public void addTimesToSchedule() {
        TIMES_TO_SEND.forEach(time -> {
            Duration nextRun = Duration.between(LocalTime.now(), time);
            if (time.isBefore(LocalTime.now())) {
                nextRun = nextRun.plusDays(1);
            }
            executors.scheduleAtFixedRate(() -> sendAll(time), nextRun.toSeconds(), TimeUnit.MINUTES.toSeconds(2), TimeUnit.SECONDS);
        });
    }

    private void sendAll(LocalTime time) {
        log.info("Start sending daily questions!");
        try {
            DailyCodingQuestion dailyCodingQuestion = leettyBotService.getDailyQuestion();
            leettyBotService.getUsersToSendDailyQuestion(time).stream()
                    .map(id -> dailyQuestionMessagePreparer.prepareMessage(dailyCodingQuestion, id))
                    .forEach(sender::execute);
        } catch (Exception e) {
            log.warn("Error while scheduled send leetcode daily question.", e);
        }
    }

    @Override
    public void close() {
        try {
            executors.shutdown();
        } catch (Exception e) {
            executors.shutdownNow();
        }
    }
}
