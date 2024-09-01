package com.ksilisk.telegram.bot.starter.action;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CallbackActionTest {
    @Test
    void executeActionWithoutCallbackData_shouldThrowException() {
        // given
        CallbackAction<?, ?, ?> callbackAction = new CallbackAction<>() {
            @Override
            public void handle(Update update, Object callbackData) {
            }

            @Override
            public Object getEvent() {
                return null;
            }
        };
        // then
        assertThrowsExactly(IllegalStateException.class, () -> callbackAction.handle(new Update()));
    }

}