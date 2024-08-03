package com.ksilisk.telegram.bot.starter.action;

import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.UPDATE_HEADER_KEY;

public interface Action<S, E> extends org.springframework.statemachine.action.Action<S, E> {
    E getEvent();

    void execute(Update update);

    @Override
    default void execute(StateContext<S, E> context) {
        Object update = context.getMessageHeaders().get(UPDATE_HEADER_KEY);
        if (update != null) {
            execute((Update) update);
        }
    }
}
