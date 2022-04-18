package com.example.demo;

import com.example.demo.Bot;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;


//@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ApiContextInitializer.init();
		Bot bot = new Bot("AncientTinder_bot", "5030909216:AAHZsk0dKDhN_AK_D7IaOD5WWxadsWSXpEw");
		bot.botConnect();
	}

}
