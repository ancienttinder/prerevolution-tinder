package com.example.demo;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo должна быть спрингом
//todo почему абстрактный?
@Component
public class KeyboardSetting {

    //todo константы с большими буквами INLINE_KEYBOARD_MARKUP
    private static InlineKeyboardMarkup INLINE_KEYBOARD_MARKUP = new InlineKeyboardMarkup();
    private static ReplyKeyboardMarkup REPLY_KEYBOARD_MARKUP = new ReplyKeyboardMarkup();
    //todo убрать?
    private static String BUTTON_TEXT;
    //todo убрать, потому что ты создаешь ниже в методах
    private static List<InlineKeyboardButton> inlineKeyboardButtonsRow = new ArrayList<>();
    //todo убрать, потому что ты создаешь ниже в методах
    private static List<KeyboardButton> keyboardButtonsRow = new ArrayList<>();
    public static Map<String, String> hashMap = new HashMap<String, String>();


    //todo это мапа должна быть в отдельном классе.
    //todo никаких русских букв в коде
    protected KeyboardSetting() {
        hashMap.put("Lover", "Влево!Вправо!Меню");
        hashMap.put("Search", "Влево!Вправо!Меню");
        hashMap.put("Menu", "Поиск!Анкета!Любимцы");
    }

    public void chooseKeyboardType(String usageName) {
//
//        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        switch (usageName) {
            case ("Lover"):
            case ("Search"):
                //todo зачем тут this?
                inlineKeyboardButtonsRow = getInlineKeyboard(hashMap.get(usageName));
                break;
            case ("Menu"):
                //todo зачем тут this?
                keyboardButtonsRow = getReplyKeyboard(hashMap.get(usageName));
                break;
            default:
                //todo тут выкидывать ошибку
        }

    }

    private List<InlineKeyboardButton> getInlineKeyboard(String options) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        String[] optinsArray = options.split("!");
        for (String s : optinsArray) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(s);
            inlineKeyboardButton.setCallbackData("Button" + s + " has been pressed");
            keyboardButtonsRow.add(inlineKeyboardButton);
        }
        return keyboardButtonsRow;
    }

    private List<KeyboardButton> getReplyKeyboard(String options) {
        List<KeyboardButton> keyboardButtonsRow = new ArrayList<>();
        String[] optionsArray = options.split("!");
        for (String s : optionsArray) {
            KeyboardButton button = new KeyboardButton();
            button.setText(s);
            keyboardButtonsRow.add(button);
        }
        return keyboardButtonsRow;
    }
}
