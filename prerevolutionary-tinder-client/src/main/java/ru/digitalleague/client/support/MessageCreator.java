package ru.digitalleague.client.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.digitalleague.client.model.NewspaperPerson;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.Callback;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageCreator {
    private final ButtonCreator buttonCreator;

    public static SendMessage createMessageTemplate(Person person) {
        return createMessageTemplate(person.getUserId());
    }

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public SendPhoto getPhotoMessage(Person person, NewspaperPerson newspaperPerson, List<Callback> callbacks) {
        SendPhoto sendPhoto = new SendPhoto(person.getUserId(), new InputFile(newspaperPerson.getNewspaperImage()));
        sendPhoto.setCaption(newspaperPerson.getCaption());
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonCreator.create(callbacks);
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;
    }
}
