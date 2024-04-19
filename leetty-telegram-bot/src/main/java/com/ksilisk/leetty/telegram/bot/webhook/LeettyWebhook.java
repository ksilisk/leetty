package com.ksilisk.leetty.telegram.bot.webhook;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.Webhook;

public interface LeettyWebhook extends Webhook {
    @Override
    default void startServer() {
    }

    @Override
    default void setInternalUrl(String internalUrl) {
    }

    @Override
    default void setKeyStore(String keyStore, String keyStorePassword) {
    }

    void updateReceived(String botPath, Update update);
}
