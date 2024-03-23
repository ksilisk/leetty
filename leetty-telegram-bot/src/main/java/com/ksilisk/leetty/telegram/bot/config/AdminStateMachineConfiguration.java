package com.ksilisk.leetty.telegram.bot.config;

import com.ksilisk.leetty.telegram.bot.event.AdminBotEvent;
import com.ksilisk.leetty.telegram.bot.state.AdminBotStates;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.redis.RedisStateMachineRepository;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import java.util.Collections;

@EnableStateMachineFactory(name = "adminBotStateMachineFactory")
class AdminStateMachineConfiguration extends AbstractStateMachineConfiguration<AdminBotStates, AdminBotEvent> {
    protected AdminStateMachineConfiguration(RedisStateMachineRepository stateMachineRepository) {
        super(stateMachineRepository, Collections.emptyList());
    }

    @Bean
    public StateMachineService<AdminBotStates, AdminBotEvent> adminStateMachineService(
            StateMachineFactory<AdminBotStates, AdminBotEvent> stateMachineFactory) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
