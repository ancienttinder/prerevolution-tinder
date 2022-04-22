package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.BotState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuitableHandler implements Handler {

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        SendMessage sendMessage = createMessageTemplate(person);
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.MENU)) {

        }
        return null;
    }

    @Override
    public BotState operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return null;
    }
}
