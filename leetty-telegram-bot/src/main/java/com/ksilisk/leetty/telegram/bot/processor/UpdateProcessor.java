package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import org.springframework.statemachine.service.StateMachineService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class UpdateProcessor<S, E> {
    public static final String UPDATE_HEADER_KEY = "update";
    public static final String CALLBACK_DATA_HEADER_KEY = "callback_data";

    protected final StateMachineService<S, E> stateMachineService;

    protected UpdateProcessor(StateMachineService<S, E> stateMachineService) {
        this.stateMachineService = stateMachineService;
    }

    public abstract void process(Update update);

    public abstract LeettyProperties.Bot getBot();
}
