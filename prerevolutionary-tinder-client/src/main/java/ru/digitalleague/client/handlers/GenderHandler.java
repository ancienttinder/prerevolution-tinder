package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.digitalleague.client.api.FieldProvider;
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
public class GenderHandler implements Handler {

    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;
    private final FieldProvider fieldProvider;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Gender Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        String messageText = messageService.getMessage("message.error");
        if (person.getBotState() == BotState.ENTER_TWO_QUESTION) {
            person.setBotState(BotState.ENTER_THREE_QUESTION);
            person.setGender(fieldProvider.field(Callback.valueOf(message)));
            messageText = person.getName() + ", " + messageService.getMessage("message.enter.description");
            restServerExchanger.save(person);
        }
        sendMessage.setText(messageText);
        log.info("End Gender Handler");
        return Collections.singletonList(sendMessage);
    }

    @Override
    public BotState operatedBotState() {
        return BotState.ENTER_TWO_QUESTION;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Arrays.asList(Callback.SIR.name(), Callback.MADAM.name());
    }
}
