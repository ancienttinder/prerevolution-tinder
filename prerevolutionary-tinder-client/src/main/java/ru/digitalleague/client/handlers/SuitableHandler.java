package ru.digitalleague.client.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.digitalleague.client.api.FieldProvider;
import ru.digitalleague.client.api.Handler;
import ru.digitalleague.client.api.ImageService;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Choice;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.support.ButtonCreator;
import ru.digitalleague.client.type.BotState;
import ru.digitalleague.client.type.Callback;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.digitalleague.client.support.MessageCreator.createMessageTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuitableHandler implements Handler {
    private static int suitablePersonIndex = 0;
    private List<Person> suitablePersons = new ArrayList<>();
    private final MessageService messageService;
    private final RestServerExchanger restServerExchanger;
    private final ImageService imageService;
    private final FieldProvider fieldProvider;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Suitable Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.MENU)) {
            if (suitablePersons.isEmpty()) {
                suitablePersons = restServerExchanger.getSuitablePerson(person.getUserId());
            }
            if(suitablePersons.isEmpty()) {
                sendMessage.setText(messageService.getMessage("message.no.person.search.term"));
                return messageList;
            }
            handleCallback(person, message);
            Person like = suitablePersons.get(suitablePersonIndex);
            File imageFile = imageService.getImage(like.getDescription());
            SendPhoto sendPhoto = new SendPhoto(person.getUserId(), new InputFile(imageFile));
            if (imageFile == null) {
                sendMessage.setText(messageService.getMessage("message.image.error"));
                return messageList;
            }
            sendPhoto.setCaption(like.getGender() + ", " + like.getName());
            List<Callback> callbacks = Arrays.asList(Callback.PREV_PERSON, Callback.MENU, Callback.NEXT_PERSON);
            InlineKeyboardMarkup inlineKeyboardMarkup = ButtonCreator.create(callbacks, fieldProvider);
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            messageList.clear();
            messageList.add(sendPhoto);
        }
        log.info("End Suitable Handler");
        return messageList;
    }

    private void handleCallback(Person person, String message) {
        int maxIndex = suitablePersons.size();
        if (Callback.NEXT_PERSON.name().equals(message)) {
            Choice choice = new Choice(person.getId(),suitablePersons.get(suitablePersonIndex).getId());
            restServerExchanger.saveChoice(choice);
            suitablePersonIndex++;
        }
        if (Callback.PREV_PERSON.name().equals(message)) {
            Choice choice = new Choice(person.getId(),suitablePersons.get(suitablePersonIndex).getId());
            restServerExchanger.deleteChoice(choice);
            suitablePersonIndex++;
        }
        if (suitablePersonIndex == maxIndex) {
            suitablePersonIndex =0;
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
        return Arrays.asList(Callback.FIND_SUITABLE_PERSON.name(),Callback.NEXT_PERSON.name(),Callback.PREV_PERSON.name());
    }
}
