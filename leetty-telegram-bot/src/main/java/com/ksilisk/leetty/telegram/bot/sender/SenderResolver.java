package com.ksilisk.leetty.telegram.bot.sender;

import com.ksilisk.leetty.telegram.bot.config.LeettyProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SenderResolver {
    private final Map<LeettyProperties.Bot, Sender> senderMap;

    public SenderResolver(List<Sender> senders) {
        this.senderMap = senders.stream().collect(Collectors.toMap(Sender::getBot, Function.identity()));
    }

    public Sender getSender(LeettyProperties.Bot bot) {
        return senderMap.get(bot);
    }
}
