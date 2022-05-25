package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.type.BotState;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Component
@RequiredArgsConstructor
public class IncorrectMessageHandler implements Handler {

    private final MessageService messageService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        SendMessage sendMessage = createMessageTemplate(person);
        sendMessage.setText(messageService.getMessage("message.incorrect"));
        return Collections.singletonList(sendMessage);
    }

    @Override
    public BotState operatedBotState() {
        return BotState.NONE;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
