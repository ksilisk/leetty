package com.ksilisk.leetty.telegram.bot.action;

import com.ksilisk.leetty.telegram.bot.service.CallbackData;
import org.springframework.statemachine.StateContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ksilisk.leetty.telegram.bot.processor.UpdateProcessor.CALLBACK_DATA_HEADER_KEY;
import static com.ksilisk.leetty.telegram.bot.processor.UpdateProcessor.UPDATE_HEADER_KEY;

public interface CallbackAction<S, E> extends Action<S, E> {
    void execute(Update update, CallbackData callbackData);

    @Override
    default void execute(Update update) {
    }

    @Override
    default void execute(StateContext<S, E> context) {
        Object update = context.getMessageHeaders().get(UPDATE_HEADER_KEY);
        Object callbackData = context.getMessageHeaders().get(CALLBACK_DATA_HEADER_KEY);
        if (update != null && callbackData != null) {
            execute((Update) update, (CallbackData) callbackData);
        }
    }
}
