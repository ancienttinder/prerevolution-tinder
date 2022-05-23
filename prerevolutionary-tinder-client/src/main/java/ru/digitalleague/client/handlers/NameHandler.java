package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.digitalleague.client.api.FieldProvider;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.support.ButtonCreator;
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
public class NameHandler implements Handler {

    private final MessageService messageService;
    private final FieldProvider fieldProvider;
    private final RestServerExchanger restServerExchanger;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Name Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        String messageText = messageService.getMessage("message.error");
        if (person.getBotState().equals(BotState.ENTER_ONE_QUESTION)) {
            person.setBotState(BotState.ENTER_TWO_QUESTION);
            person.setName(message);
            List<Callback> callbacks = Arrays.asList(Callback.MADAM, Callback.SIR);
            InlineKeyboardMarkup inlineKeyboardMarkup = ButtonCreator.create(callbacks, fieldProvider);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            messageText = message + ", " + messageService.getMessage("message.enter.gender");
            restServerExchanger.save(person);
        }
        sendMessage.setText(messageText);
        log.info("End Name Handler");
        return Collections.singletonList(sendMessage);
    }

    @Override
    public BotState operatedBotState() {
        return BotState.ENTER_ONE_QUESTION;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
