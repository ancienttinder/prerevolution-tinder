package ru.digitalleague.client.support;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.Callback;

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

    public static InlineKeyboardButton createInlineKeyboardButton(String text, Callback callback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callback.name());
        return inlineKeyboardButton;
    }
}
