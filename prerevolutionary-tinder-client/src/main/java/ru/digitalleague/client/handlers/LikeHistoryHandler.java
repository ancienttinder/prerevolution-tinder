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
public class LikeHistoryHandler implements Handler {

    private final MessageService messageService;
    private final ImageService imageService;
    private final RestServerExchanger restServerExchanger;
    private final FieldProvider fieldProvider;
    private static int likeIndex = 0;
    private List<Person> likeHistory = new ArrayList<>();

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message) {
        log.info("Start Like History Handler");
        SendMessage sendMessage = createMessageTemplate(person);
        List<PartialBotApiMethod<? extends Serializable>> messageList = new ArrayList<>();
        messageList.add(sendMessage);
        if (person.getBotState().equals(BotState.MENU)) {
            likeHistory = restServerExchanger.getLikeHistory(person.getUserId());
            if(likeHistory.isEmpty()) {
                sendMessage.setText(messageService.getMessage("message.no.like.history"));
                return messageList;
            }
            person.setBotState(BotState.LIKE_HISTORY);
        }
        if (person.getBotState().equals(BotState.LIKE_HISTORY)) {
            handleCallback(message);
            Person like = likeHistory.get(likeIndex);
            File imageFile = imageService.getImage(like.getDescription());
            SendPhoto sendPhoto = new SendPhoto(person.getUserId(), new InputFile(imageFile));
            if (imageFile == null) {
                sendMessage.setText(messageService.getMessage("message.image.error"));
                return messageList;
            }
            sendPhoto.setCaption(like.getGender() + ", " + like.getName());
            List<Callback> callbacks = Arrays.asList(Callback.PREVIOUS, Callback.MENU, Callback.NEXT);
            InlineKeyboardMarkup inlineKeyboardMarkup = ButtonCreator.create(callbacks, fieldProvider);
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
            messageList.clear();
            messageList.add(sendPhoto);
        }
        log.info("End Like History Handler");
        return messageList;
    }

    private void handleCallback(String message) {
        int maxIndex = likeHistory.size();
        if (Callback.NEXT.name().equals(message)) {
            likeIndex++;
        }
        if (Callback.PREVIOUS.name().equals(message)) {
            likeIndex--;
        }
        if (likeIndex == maxIndex) {
            likeIndex =0;
        }
        if (likeIndex == -1) {
            likeIndex = maxIndex - 1;
        }
    }

    @Override
    public BotState operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Arrays.asList(Callback.LIKE_HISTORY.name(),Callback.PREVIOUS.name(),Callback.NEXT.name());
    }
}
