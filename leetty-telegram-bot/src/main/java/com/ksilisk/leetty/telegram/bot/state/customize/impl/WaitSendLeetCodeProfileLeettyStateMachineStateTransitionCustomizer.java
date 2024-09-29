package com.ksilisk.leetty.telegram.bot.state.customize.impl;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.leetty.telegram.bot.state.customize.LeettyStateMachineStateTransitionCustomizer;
import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.MESSAGE;
import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.SET_LEETCODE_PROFILE;
import static com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard.UPDATE_LEETCODE_PROFILE;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_NEW_COMMAND;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_SEND_LEETCODE_PROFILE;

@Component
public class WaitSendLeetCodeProfileLeettyStateMachineStateTransitionCustomizer implements LeettyStateMachineStateTransitionCustomizer {
    private final Set<LeettyBotEvent> skippableEvents = Set.of(MESSAGE, SET_LEETCODE_PROFILE);

    @Override
    public StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> customize(StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> transitionConfigurer,
                                                                                       DelegateAction<LeettyBotStates, LeettyBotEvent> delegateAction,
                                                                                       DelegateGuard<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> delegateGuard) throws Exception {
        for (LeettyBotEvent event : LeettyBotEvent.values()) {
            if (!skippableEvents.contains(event)) {
                if (event.isCallback()) {
                    transitionConfigurer = transitionConfigurer
                            .withInternal()
                            .source(WAIT_SEND_LEETCODE_PROFILE)
                            .event(event)
                            .action(delegateAction)
                            .and();
                } else {
                    transitionConfigurer = transitionConfigurer
                            .withExternal()
                            .source(WAIT_SEND_LEETCODE_PROFILE)
                            .target(WAIT_NEW_COMMAND)
                            .event(event)
                            .action(delegateAction)
                            .and();
                }
            }
        }
        return transitionConfigurer.withExternal()
                .source(WAIT_SEND_LEETCODE_PROFILE)
                .event(MESSAGE)
                .guard(delegateGuard.getGuard(UPDATE_LEETCODE_PROFILE))
                .target(WAIT_NEW_COMMAND)
                .and();
    }
}
