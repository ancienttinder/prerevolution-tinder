package com.example.demo.controller;

import com.example.demo.Bot;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.api.objects.Update;

@RestController
public class BotController {
    private final Bot bot;

    //Почему не подтягивается спринг тут?
    public BotController(Bot bot) {
        this.bot = bot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void onUpdateRecieved(@RequestBody Update update) {
        bot.onUpdateReceived(update);
    }
}
