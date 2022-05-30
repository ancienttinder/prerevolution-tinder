package ru.digitalleague.client.support;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.digitalleague.client.api.FieldProvider;
import ru.digitalleague.client.type.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//todo приватный конструктор
public class ButtonCreator {
    public static InlineKeyboardMarkup create(List<Callback> buttons, FieldProvider fieldProvider) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                buttons.stream().map(c -> createInlineKeyboardButton(fieldProvider.field(c), c))
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
}
