package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
@AllArgsConstructor
@ConfigurationProperties("application.properties")
@NoArgsConstructor
public class Bot extends TelegramWebhookBot {

    //todo @@Slf4j, для ее добавления ты должен взять logback-spring.xml, добавить в /resources
    private static final Logger log = Logger.getLogger(Bot.class);
    //todo это плохо, нужно пеереложить в /resources, погугли как тянуть из resounrces файлы
//    @Value("${file_path}")
//    private String FILE_PATH;
//
//    private File FILE = new File(FILE_PATH);

    private File FILE = new File("C:\\Users\\tilikyan\\Desktop\\photo\\1.jpg");

    private BufferedImage image;
    private String userName;
    private String token;
    private String path;

    @Value("${reconnect_pause}")
    private int RECONNECT_PAUSE;

    private KeyboardSetting keyBoard;

    public Bot(String userName, String token, String path) {
        this.userName = userName;
        this.token = token;
        this.path = path;
    }
    //todo тянуть их из application properties
    //todo почитай про single responsibility principle
//    @Setter
//    @Getter
//    String userName;
//    @Setter
//    @Getter
//    String token;

//    @Override
//    public void onUpdateReceived(Update update) {
//
////        try {
////            SendPhoto msg = new SendPhoto()
////                    .setChatId(update.getMessage().getChatId())
////                    .setNewPhoto("Приятного", putTextToForm(update.getMessage().getText()));
////            sendPhoto(msg);
////        } catch (TelegramApiException | IOException e) {
////            e.printStackTrace();
////        }
//        if (update.getMessage() != null && update.getMessage().hasText()) {
//            long chat_id = update.getMessage().getChatId();
//
//            try {
//                execute(new SendMessage(chat_id, "Asd " + update.getMessage().getText()));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
//        log.debug("new Update recieve");
//    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();

            try {
                execute(new SendMessage(chat_id, "Asd " + update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        log.debug("new Update recieve");
        return null;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotPath() {
        return path;
    }

    //todo разобраться что это такое. Вынести в отдельный класс
    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            log.info("TelegramAPI started. Look for messages");
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

    //todo c большой буквы методы не называются. Нужно создать сервис, поместить код туда. В названии должен быть глагол, вызвать этот метод
    public InputStream putTextToForm(String txt) throws IOException {
        image = ImageIO.read(FILE);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 52));
        //int x = g.getFontMetrics(new Font("Courier New", Font.BOLD, 52));
        g.drawString(txt, (image.getWidth() / 2), 50);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

}
