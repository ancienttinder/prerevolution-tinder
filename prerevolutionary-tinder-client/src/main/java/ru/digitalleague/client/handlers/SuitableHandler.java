package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.builder.PersonBuilder;
import ru.digitalleague.client.model.Choice;
import ru.digitalleague.client.model.NewspaperPerson;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.support.MessageCreator;
import ru.digitalleague.client.type.BotState;
import ru.digitalleague.client.type.Callback;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuitableHandler implements Handler {

    private int suitablePersonIndex = 0;
    private List<NewspaperPerson> suitablePersons = new ArrayList<>();
    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;
    private final PersonBuilder personBuilder;
    private final MessageCreator messageCreator;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Suitable Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.MENU)) {
            suitablePersons = restServerExchanger.getSuitablePerson(person.getUserId());
            suitablePersonIndex = 0;
            if (suitablePersons.isEmpty()) {
                sendMessage.setText(messageService.getMessage("message.no.person.search.term"));
                return messageList;
            }
            person = personBuilder.build(person, BotState.SEARCH);
            restServerExchanger.save(person);
        }
        if (person.getBotState().equals(BotState.SEARCH)) {
            handleCallback(person, message);
            NewspaperPerson newspaperPerson = suitablePersons.get(suitablePersonIndex);
            File imageFile = newspaperPerson.getNewspaperImage();
            if (imageFile == null) {
                sendMessage.setText(messageService.getMessage("message.image.error"));
                return messageList;
            }
            SendPhoto sendPhoto = messageCreator.getPhotoMessage(person, newspaperPerson, Arrays.asList(Callback.PREV_PERSON, Callback.MENU, Callback.NEXT_PERSON));
            messageList.clear();
            messageList.add(sendPhoto);
        }
        log.info("End Suitable Handler");
        return messageList;
    }

    private void handleCallback(Person person, String message) {
        int maxIndex = suitablePersons.size();
        if (Callback.NEXT_PERSON.name().equals(message)) {
            Choice choice = new Choice((long) person.getId(), suitablePersons.get(suitablePersonIndex).getPersonId());
            restServerExchanger.saveChoice(choice);
            suitablePersonIndex++;
        }
        if (Callback.PREV_PERSON.name().equals(message)) {
            Choice choice = new Choice((long) person.getId(), suitablePersons.get(suitablePersonIndex).getPersonId());
            restServerExchanger.deleteChoice(choice);
            suitablePersonIndex++;
        }
        if (suitablePersonIndex == maxIndex) {
            suitablePersonIndex = 0;
        }
        if (suitablePersonIndex == -1) {
            suitablePersonIndex = maxIndex - 1;
        }
    }

    @Override
    public BotState operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Arrays.asList(Callback.FIND_SUITABLE_PERSON.name(), Callback.NEXT_PERSON.name(), Callback.PREV_PERSON.name());
    }
}
