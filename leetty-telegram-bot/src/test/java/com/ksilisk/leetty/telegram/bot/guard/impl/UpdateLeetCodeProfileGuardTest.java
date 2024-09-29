package com.ksilisk.leetty.telegram.bot.guard.impl;

import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard.UPDATE_LEETCODE_PROFILE;

class UpdateLeetCodeProfileGuardTest {
    @Test
    void getGuard_shouldReturnValidGuard() {
        // given
        UpdateLeetCodeProfileGuard updateLeetCodeProfileGuard =
                new UpdateLeetCodeProfileGuard(null, null, null, null);
        // when
        LeettyBotGuard guard = updateLeetCodeProfileGuard.getGuard();
        // then
        Assertions.assertEquals(UPDATE_LEETCODE_PROFILE, guard);

    }
}