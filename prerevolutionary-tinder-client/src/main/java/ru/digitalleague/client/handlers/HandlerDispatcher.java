package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.BotState;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandlerDispatcher {
    private final List<Handler> handlers;
    private final IncorrectMessageHandler incorrectMessageHandler;
    private final RestServerExchanger restServerExchanger;

    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        try {
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();
                final long userId = message.getFrom().getId();
                Person person = getPerson(userId);
                log.info("Handler for:{}", person);
                return getHandlerByState(person.getBotState()).handle(person, message.getText());
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final long userId = callbackQuery.getFrom().getId();
                final Person person = restServerExchanger.getPersonByUserId(String.valueOf(userId));
                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(person, callbackQuery.getData());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return incorrectMessageHandler.handle(new Person(), "");
        }
    }

    private Handler getHandlerByState(BotState botState) {

        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(botState))
                .findAny()
                .orElse(incorrectMessageHandler);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(q -> q.equals(query)))
                .findAny()
                .orElse(incorrectMessageHandler);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

    private Person getPerson(long userId) {
        Person person = restServerExchanger.getPersonByUserId(String.valueOf(userId));
        if (person == null) {
            person = new Person();
            person.setUserId(String.valueOf(userId));
            person.setBotState(BotState.START);
            person = restServerExchanger.save(person);
            log.info("Create person: {}", person);
        }
        return person;
    }
}
