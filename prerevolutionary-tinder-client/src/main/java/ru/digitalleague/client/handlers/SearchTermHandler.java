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
public class SearchTermHandler implements Handler {

    private final MessageService messageService;
    private final FieldProvider fieldProvider;
    private final ImageService imageService;
    private final RestServerExchanger restServerExchanger;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        SendMessage sendMessage = createMessageTemplate(person);
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.ENTER_FOUR_QUESTION)) {
            person.setBotState(BotState.MENU);
            person.setSearchTerm(message);
            person = restServerExchanger.save(person);
            File imageLocation = imageService.getImage(person.getDescription());
            if (imageLocation == null) {
                sendMessage.setText(messageService.getMessage("message.image.error"));
                messageList.add(sendMessage);
                return messageList;
            }
            SendPhoto sendPhoto = new SendPhoto(person.getUserId(),new InputFile());
            sendPhoto.setCaption(person.getGender() + ", " + person.getName());
            messageList.clear();
            messageList.add(sendPhoto);
            List<Callback> callbacks = Arrays.asList(Callback.EDIT, Callback.FIND_SUITABLE_PERSON,Callback.LIKE_HISTORY);
            InlineKeyboardMarkup inlineKeyboardMarkup = ButtonCreator.create(callbacks, fieldProvider);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
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
