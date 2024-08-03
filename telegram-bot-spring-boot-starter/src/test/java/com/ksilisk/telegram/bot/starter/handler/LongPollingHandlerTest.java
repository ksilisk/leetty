package com.ksilisk.telegram.bot.starter.handler;

import com.ksilisk.telegram.bot.starter.config.BotProperties;
import com.ksilisk.telegram.bot.starter.processor.UpdateProcessor;
import com.ksilisk.telegram.bot.starter.sender.Sender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

class LongPollingHandlerTest {
    @Test
    void clearWebhookWhenStart_shouldExecuteClearWebhookInSender() throws TelegramApiRequestException {
        // given
        Sender sender = Mockito.mock(Sender.class);
        Mockito.when(sender.execute(new DeleteWebhook())).thenReturn(true);
        UpdateProcessor updateProcessor = Mockito.mock(UpdateProcessor.class);
        LongPollingHandler handler = new LongPollingHandler(new BotProperties(), updateProcessor, sender) {
            @Override
            protected void processUpdate(Update update) {
            }
        };
        // when
        handler.clearWebhook();
        // then
        Mockito.verify(sender).execute(ArgumentMatchers.isA(DeleteWebhook.class));
    }

    @Test
    void processUpdateThrowsException_shouldNotThrowException() {
        // given
        Sender sender = Mockito.mock(Sender.class);
        UpdateProcessor updateProcessor = Mockito.mock(UpdateProcessor.class);
        Update update = new Update();
        LongPollingHandler handler = new LongPollingHandler(new BotProperties(), updateProcessor, sender) {
            @Override
            protected void processUpdate(Update update) {
                throw new RuntimeException("some ex");
            }
        };
        // then
        Assertions.assertDoesNotThrow(() -> handler.onUpdateReceived(update));
    }
}