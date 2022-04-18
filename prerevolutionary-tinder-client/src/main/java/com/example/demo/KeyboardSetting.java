package com.example.demo;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KeyboardSetting {

    private static InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
    private static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private static String buttonsText;
    private static List<InlineKeyboardButton> inlineKeyboardButtonsRow = new ArrayList<>();
    private static List<KeyboardButton> keyboardButtonsRow = new ArrayList<>();
    public static Map<String,String> hashMap = new HashMap<String,String>();


    protected KeyboardSetting(){
        hashMap.put("Lover","Влево!Вправо!Меню");
        hashMap.put("Search","Влево!Вправо!Меню");
        hashMap.put("Menu","Поиск!Анкета!Любимцы");
    }

    public void ChooseKeyboardType(String usageName){
//        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        switch (usageName){
            case ("Lover"):
            case ("Search"):
                inlineKeyboardButtonsRow = this.GetInlineKeyboard(hashMap.get(usageName));
                break;
            case ("Menu"):
                keyboardButtonsRow = this.GetReplyKeyboard(hashMap.get(usageName));
                break;
        }

    }

    private List<InlineKeyboardButton> GetInlineKeyboard(String options){
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

    private List<KeyboardButton> GetReplyKeyboard(String options){
        List<KeyboardButton> keyboardButtonsRow = new ArrayList<>();
        String[] optinsArray = options.split("!");
        for (String s : optinsArray) {
            KeyboardButton button = new KeyboardButton();
            button.setText(s);
            keyboardButtonsRow.add(button);
        }
        return keyboardButtonsRow;
    }
}
