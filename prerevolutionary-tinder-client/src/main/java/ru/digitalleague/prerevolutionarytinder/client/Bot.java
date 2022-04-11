package ru.digitalleague.prerevolutionarytinder.client;

import com.mashape.unirest.http.Unirest;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Getter
@Setter
@Component
public class Bot extends TelegramLongPollingBot {

    private String botUsername = "AncientTinder_bot";
    private String botToken = "5030909216:AAHZsk0dKDhN_AK_D7IaOD5WWxadsWSXpEw";

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update){
        update.getUpdateId();

        SendMessage sendMessage = null;
        new SendMessage().setChatId(String.valueOf(update.getMessage().getChatId()));
        SendPhoto sendPhoto = new SendPhoto();
        if(update.getMessage().getText().equals("Привет")){
            sendMessage.setText("Приветики");
            try{
                execute(sendMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

        if(update.getMessage().getText().equals("Котики")){
            /*String result = Unirest.get("https://aws.random.cat/meow")
                    .asJson()
                    .getBody()
                    .getObject()
                    .getJSONObject("file")
                    .getJSONArray("file")
                    .get(0).toString();*/
            //Image image = ImageIO.read(new URL(result));
            //sendPhoto.setPhoto(result);
            Dto body = Unirest.get("https://aws.random.cat/meow")
                    .asObject(Dto.class)
                    .getBody();
            //System.out.println(body.getFile());
            sendMessage.setText(body.getFile());
            try{
                execute(sendMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

//        log.debug("Receive new Update. updateID: " + update.getUpdateId());

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Привет.\n Напиши \"Привет\", и бот поздоровается с тобой.\n Напиши \"Котики\", и он покажет тебе картинку котика");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
