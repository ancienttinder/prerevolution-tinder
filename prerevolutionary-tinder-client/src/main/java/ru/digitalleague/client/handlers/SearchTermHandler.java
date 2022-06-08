package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.NewspaperPerson;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.support.MessageCreator;
import ru.digitalleague.client.type.BotState;
import ru.digitalleague.client.type.Callback;
import ru.digitalleague.client.type.Gender;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchTermHandler implements Handler {

    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;
    private final MessageCreator messageCreator;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Search Term Handler");
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        SendMessage sendMessage = createMessageTemplate(person);
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.ENTER_FOUR_QUESTION)) {
            person = new Person(person.getId(), person.getGender(), person.getName(), person.getDescription(), Gender.getFromSearchTerm(Callback.valueOf(message)), person.getUserId(), BotState.MENU);
            person = restServerExchanger.save(person);
            NewspaperPerson newspaperPerson = restServerExchanger.getPhotoPersonByUserId(person.getUserId());
            File imageLocation = newspaperPerson.getNewspaperImage();
            if (imageLocation == null) {
                sendMessage.setText(messageService.getMessage("message.image.error"));
                messageList.add(sendMessage);
                return messageList;
            }
            SendPhoto sendPhoto = messageCreator.getPhotoMessage(person, newspaperPerson, Arrays.asList(Callback.EDIT, Callback.FIND_SUITABLE_PERSON, Callback.LIKE_HISTORY));
            messageList.clear();
            messageList.add(sendPhoto);
        }
        log.info("End Search Term Handler");
        return messageList;
    }

    @Override
    public BotState operatedBotState() {
        return BotState.ENTER_FOUR_QUESTION;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Arrays.asList(Callback.MADAM_SEARCH.name(), Callback.SIR_SEARCH.name(), Callback.ALL_SEARCH.name());
    }
}
