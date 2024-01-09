package com.ksilisk.leetty.telegram.bot;

import com.ksilisk.leetty.telegram.bot.feign.DemoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Test {
    public final DemoClient demoClient;
}
