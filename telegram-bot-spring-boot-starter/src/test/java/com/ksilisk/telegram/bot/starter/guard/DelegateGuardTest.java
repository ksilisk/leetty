package com.ksilisk.telegram.bot.starter.guard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DelegateGuardTest {
    @Test
    void executeNotAllowedEvaluate_shouldThrowException() {
        // given
        DelegateGuard<?, ?, ?> delegateGuard = new DelegateGuard<>();

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> delegateGuard.evaluate(null));
    }

}