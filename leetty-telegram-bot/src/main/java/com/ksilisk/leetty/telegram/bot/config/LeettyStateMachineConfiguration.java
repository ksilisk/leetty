package com.ksilisk.leetty.telegram.bot.config;

import com.ksilisk.leetty.telegram.bot.event.LeettyBotEvent;
import com.ksilisk.leetty.telegram.bot.guard.LeettyBotGuard;
import com.ksilisk.leetty.telegram.bot.state.LeettyBotStates;
import com.ksilisk.telegram.bot.starter.action.Action;
import com.ksilisk.telegram.bot.starter.action.DelegateAction;
import com.ksilisk.telegram.bot.starter.config.StateMachineConfiguration;
import com.ksilisk.telegram.bot.starter.config.customize.StateMachineStateTransitionCustomizer;
import com.ksilisk.telegram.bot.starter.guard.DelegateGuard;
import com.ksilisk.telegram.bot.starter.guard.Guard;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import java.util.EnumSet;
import java.util.List;

import static com.ksilisk.leetty.telegram.bot.state.LeettyBotStates.INITIAL;

@EnableStateMachineFactory(name = "leettyBotStateMachineFactory")
class LeettyStateMachineConfiguration extends StateMachineConfiguration<LeettyBotStates, LeettyBotEvent, LeettyBotGuard> {
    protected LeettyStateMachineConfiguration(RedisStateMachineRepository stateMachineRepository,
                                              List<Action<LeettyBotStates, LeettyBotEvent>> actionList,
                                              List<Guard<LeettyBotStates, LeettyBotEvent, LeettyBotGuard>> guardList,
                                              List<StateMachineStateTransitionCustomizer<LeettyBotStates, LeettyBotEvent, LeettyBotGuard>> stateMachineStateTransitionCustomizers) {
        super(stateMachineRepository, new DelegateAction<>(actionList), new DelegateGuard<>(guardList), stateMachineStateTransitionCustomizers);
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
}
