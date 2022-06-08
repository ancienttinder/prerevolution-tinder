package ru.digitalleague.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.digitalleague.client.handlers.HandlerDispatcher;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.token}")
    private String botToken;

    private final HandlerDispatcher handlerDispatcher;

    public void onUpdateReceived(Update update) {
        log.info("New Update");
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = handlerDispatcher.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
                if (response instanceof SendPhoto) {
                    executeImgWithExceptionCheck((SendPhoto) response);
                }
            });
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            log.info(sendMessage.getText());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void executeImgWithExceptionCheck(SendPhoto sendMessage) {
        try {
            log.info(sendMessage.getPhoto().getMediaName());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

}
