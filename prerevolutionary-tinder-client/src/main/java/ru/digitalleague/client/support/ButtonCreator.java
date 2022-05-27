package ru.digitalleague.client.support;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.digitalleague.client.api.FieldProvider;
import ru.digitalleague.client.type.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//todo приватный конструктор
public class ButtonCreator {
    //todo пустые строки
    public static InlineKeyboardMarkup create(List<Callback> buttons, FieldProvider fieldProvider) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                buttons.stream().map(c -> createInlineKeyboardButton(fieldProvider.field(c), c) )
                        .collect(Collectors.toList());

        inlineKeyboardMarkup.setKeyboard(Arrays.asList(inlineKeyboardButtonsRowOne));

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, Callback callback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callback.name());
        return inlineKeyboardButton;
    }

    //todo не используется
    public static ReplyKeyboardMarkup createReplyKeyboardMarkup(List<Callback> callbacks, FieldProvider fieldProvider) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (Callback callback : callbacks) {
            KeyboardRow row = new KeyboardRow();
            row.add(fieldProvider.field(callback));
            keyboard.add(row);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
