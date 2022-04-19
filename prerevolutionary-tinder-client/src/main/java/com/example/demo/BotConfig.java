package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Value("${bot_name}")
    private String botName;

    @Value("${bot_token}")
    private String botToken;

    @Value("${bot_path}")
    private String botPath;

    @Bean
    public Object createBot() {

        //todo вынести это в app prop
        Bot bot = new Bot(botName, botToken, botPath);
        bot.botConnect();
        return null;
    }
}
