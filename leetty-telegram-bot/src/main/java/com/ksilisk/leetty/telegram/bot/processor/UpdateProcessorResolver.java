package com.ksilisk.leetty.telegram.bot.processor;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UpdateProcessorResolver {
    private final Map<LeettyProperties.Bot, UpdateProcessor<?, ?>> botProcessorMap;

    public UpdateProcessorResolver(List<UpdateProcessor<?, ?>> updateProcessors) {
        this.botProcessorMap = updateProcessors.stream()
                .collect(Collectors.toMap(UpdateProcessor::getBot, Function.identity()));
    }

    public UpdateProcessor<?, ?> getUpdateProcessorByBot(LeettyProperties.Bot bot) {
        return botProcessorMap.get(bot);
    }
}
