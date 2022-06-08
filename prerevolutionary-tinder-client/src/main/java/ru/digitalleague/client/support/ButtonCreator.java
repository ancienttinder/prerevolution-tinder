package ru.digitalleague.client.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.digitalleague.client.service.MessageService;
import ru.digitalleague.client.type.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ButtonCreator {
    private final MessageService messageService;

    public InlineKeyboardMarkup create(List<Callback> buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                buttons.stream().map(c -> createInlineKeyboardButton(messageService.getMessage(c.getMessageId()), c))
                        .collect(Collectors.toList());
        inlineKeyboardMarkup.setKeyboard(Arrays.asList(inlineKeyboardButtonsRowOne));
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton createInlineKeyboardButton(String text, Callback callback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callback.name());
        return inlineKeyboardButton;
    }
}
