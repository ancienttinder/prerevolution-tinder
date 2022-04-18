package com.example.demo;

import lombok.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Аннотация @Component необходима, чтобы наш класс распознавался Spring, как полноправный Bean
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {

    //todo @@Slf4j, для ее добавления ты должен взять logback-spring.xml, добавить в /resources
    private static final Logger log = Logger.getLogger(Bot.class);
    //todo это плохо, нужно пеереложить в /resources, погугли как тянуть из resounrces файлы
    private static final File file = new File("C:\\Users\\tilikyan\\Desktop\\photo\\me.jpg");
    private static BufferedImage image;
    //todo упустил private, это переменная должны тянуться из app.prop, и тянунять @Value
    final int RECONNECT_PAUSE =10000;
    private static KeyboardSetting keyBoard;

    //todo тянуть их из application properties
    //todo почитай про single responsibility principle
    @Setter
    @Getter
    String userName;
    @Setter
    @Getter
    String token;

    @Override
    public void onUpdateReceived(Update update) {


//        try {
//
//            SendPhoto msg = new SendPhoto()
//                    .setChatId(update.getMessage().getChatId())
//                    .setNewPhoto("Приятного", TextToForm(update.getMessage().getText()));
//            sendPhoto(msg);
//        } catch (TelegramApiException | IOException e) {
//            e.printStackTrace();
//        }
        String inputText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (inputText.startsWith("/start")) {
//            SendMessage message = new SendMessage();
//            message.setChatId(chatId);
//            message.setText("Привет.\n Напиши \"Привет\", и бот поздоровается с тобой.\n Напиши \"Котики\", и он покажет тебе картинку котика");
            KeyboardSetting a = new KeyboardSetting() {
                @Override
                public void ChooseKeyboardType(String usageName) {
                    super.ChooseKeyboardType(usageName);
                }
            };
        }
        log.debug("new Update recieve");
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
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
    public InputStream TextToForm(String txt) throws IOException {
        image = ImageIO.read(file);


        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 52));
        //int x = g.getFontMetrics(new Font("Courier New", Font.BOLD, 52));
        g.drawString(txt, (image.getWidth()/2 ), 50);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

}
