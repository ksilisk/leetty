package com.ksilisk.leetty.telegram.bot.controller;

import com.ksilisk.leetty.telegram.bot.config.LeettyTelegramBotWebhookConfiguration;
import com.ksilisk.leetty.telegram.bot.webhook.LeettyWebhook;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping("/callback")
@ConditionalOnBean(LeettyTelegramBotWebhookConfiguration.class)
public class WebhookController {
    private final LeettyWebhook leettyWebhook;

    @GetMapping("/{botPath}")
    public String testBotPathWork(@PathVariable("botPath") String botPath) {
        return "I'am work bot:" + botPath;
    }

    @PostMapping("/{botPath}")
    public void handleUpdate(@PathVariable("botPath") String botPath, @RequestBody Update update) {
        leettyWebhook.updateReceived(botPath, update);
    }

}
