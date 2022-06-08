package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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
public class DescriptionHandler implements Handler {

    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;
    private final ButtonCreator buttonCreator;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Description Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        String messageText = messageService.getMessage("message.error");
        if (person.getBotState() == operatedBotState()) {
            person = new Person(person.getId(), person.getGender(), person.getName(), message, person.getSearchTerm(), person.getUserId(), BotState.ENTER_FOUR_QUESTION);
            messageText = messageService.getMessage("message.enter.search.term");
            List<Callback> callbacks = Arrays.asList(Callback.MADAM_SEARCH, Callback.SIR_SEARCH, Callback.ALL_SEARCH);
            InlineKeyboardMarkup inlineKeyboardMarkup = buttonCreator.create(callbacks);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            restServerExchanger.save(person);
        }
        sendMessage.setText(messageText);
        log.info("End Description Handler");
        return Collections.singletonList(sendMessage);
    }

    @Override
    public BotState operatedBotState() {
        return BotState.ENTER_THREE_QUESTION;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
