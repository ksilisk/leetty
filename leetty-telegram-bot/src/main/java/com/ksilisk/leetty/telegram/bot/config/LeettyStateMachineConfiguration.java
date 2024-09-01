package com.ksilisk.leetty.telegram.bot.config;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.action.Action;
import com.ksilisk.telegram.bot.starter.config.StateMachineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import java.util.EnumSet;
import java.util.List;

import static com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent.*;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.INITIAL;
import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.WAIT_NEW_COMMAND;

@EnableStateMachineFactory(name = "leettyBotStateMachineFactory")
class LeettyStateMachineConfiguration extends StateMachineConfiguration<LeettyBotStates, LeettyBotEvent> {
    protected LeettyStateMachineConfiguration(RedisStateMachineRepository stateMachineRepository,
                                              List<Action<LeettyBotStates, LeettyBotEvent>> actionList) {
        super(stateMachineRepository, actionList);
    }

    @Bean
    public StateMachineService<LeettyBotStates, LeettyBotEvent> leettyStateMachineService(
            StateMachineFactory<LeettyBotStates, LeettyBotEvent> stateMachineFactory) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<LeettyBotStates, LeettyBotEvent> states) throws Exception {
        states.withStates()
                .initial(INITIAL)
                .states(EnumSet.allOf(LeettyBotStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<LeettyBotStates, LeettyBotEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(INITIAL)
                .target(WAIT_NEW_COMMAND)
                .event(START_COMMAND)
                .action(eventActionMap.get(START_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(START_COMMAND)
                .action(eventActionMap.get(START_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(SETTINGS_COMMAND)
                .action(eventActionMap.get(SETTINGS_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(DAILY_QUESTION_COMMAND)
                .action(eventActionMap.get(DAILY_QUESTION_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(INVALID)
                .action(eventActionMap.get(INVALID))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(HELP_COMMAND)
                .action(eventActionMap.get(HELP_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(SET_SEND_DAILY_TIME)
                .action(eventActionMap.get(SET_SEND_DAILY_TIME))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(UPDATE_SEND_DAILY_TIME)
                .action(eventActionMap.get(UPDATE_SEND_DAILY_TIME))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(INLINE_QUERY_COMMAND)
                .action(eventActionMap.get(INLINE_QUERY_COMMAND))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_ACCEPTANCE)
                .action(eventActionMap.get(GET_QUESTION_ACCEPTANCE))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_CONSTRAINTS)
                .action(eventActionMap.get(GET_QUESTION_CONSTRAINTS))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_DIFFICULTY)
                .action(eventActionMap.get(GET_QUESTION_DIFFICULTY))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_HINTS)
                .action(eventActionMap.get(GET_QUESTION_HINTS))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_EXAMPLES)
                .action(eventActionMap.get(GET_QUESTION_EXAMPLES))
                .and()
                .withInternal()
                .source(WAIT_NEW_COMMAND)
                .event(GET_QUESTION_TOPICS)
                .action(eventActionMap.get(GET_QUESTION_TOPICS));
    }
}
