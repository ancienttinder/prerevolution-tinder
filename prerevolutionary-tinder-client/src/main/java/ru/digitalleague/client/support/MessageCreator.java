package ru.digitalleague.client.support;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.digitalleague.client.model.Person;

//todo приватный конструктор
public class MessageCreator {
    public static SendMessage createMessageTemplate(Person person) {
        return createMessageTemplate(person.getUserId());
    }

    public static SendMessage createMessageTemplate(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }
}
