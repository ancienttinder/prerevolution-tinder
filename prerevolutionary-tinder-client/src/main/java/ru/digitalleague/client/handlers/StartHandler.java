package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.type.BotState;
import ru.digitalleague.client.type.Callback;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler implements Handler {

    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        SendMessage hello = createMessageTemplate(person);
        String firstMessage = messageService.getMessage("message.hello");
        if (message.equals(Callback.EDIT)) {
            firstMessage = messageService.getMessage("message.edit.person");
        }
        hello.setText(firstMessage);
        SendMessage enterNameMessage = createMessageTemplate(person);
        enterNameMessage.setText(messageService.getMessage("message.enter.name"));
        person.setBotState(BotState.ENTER_ONE_QUESTION);

        return Arrays.asList(hello, enterNameMessage);
    }

    @Override
    public BotState operatedBotState() {
        return BotState.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.singletonList(Callback.EDIT.name());
    }
}
