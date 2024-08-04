package com.ksilisk.telegram.bot.starter.action;

import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.CALLBACK_DATA_HEADER_KEY;
import static com.ksilisk.telegram.bot.starter.processor.UpdateProcessor.UPDATE_HEADER_KEY;

public interface CallbackAction<S, E, T> extends Action<S, E> {
    void execute(Update update, T callbackData);

    @Override
    default void execute(Update update) {
    }

    @Override
    default void execute(StateContext<S, E> context) {
        Object update = context.getMessageHeaders().get(UPDATE_HEADER_KEY);
        Object callbackData = context.getMessageHeaders().get(CALLBACK_DATA_HEADER_KEY);
        if (update != null && callbackData != null) {
            execute((Update) update, (T) callbackData);
        }
    }
}
