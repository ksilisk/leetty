package com.ksilisk.telegram.bot.starter.guard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateContext;

class GuardTest {
    @Test
    void executeGuardThrowsException_shouldReturnFalse() {
        // given
        Guard<?, ?, ?> guard = new Guard<>() {
            @Override
            public Object getGuard() {
                return null;
            }

            @Override
            public boolean guard(StateContext<Object, Object> context) {
                throw new IllegalStateException();
            }
        };
        // when
        boolean result = guard.evaluate(null);
        // then
        Assertions.assertFalse(result);
    }
}