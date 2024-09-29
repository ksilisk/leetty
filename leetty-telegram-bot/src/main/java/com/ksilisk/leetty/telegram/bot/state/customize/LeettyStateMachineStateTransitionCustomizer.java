package com.ksilisk.leetty.telegram.bot.state.customize;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.config.customize.StateMachineStateTransitionCustomizer;

public interface LeettyStateMachineStateTransitionCustomizer extends StateMachineStateTransitionCustomizer<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> {
}
