package ru.digitalleague.prerevolutionarytinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.digitalleague.prerevolutionarytinder.service.MessageService;

@Configuration
public class MessageServiceConfig {

    @Bean
    public MessageService messageService() {
        return new MessageService(messageSource());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        ResourceBundleMessageSource commonMessageSource = new ResourceBundleMessageSource();
        commonMessageSource.setBasename("common_messages");
        commonMessageSource.setDefaultEncoding("UTF-8");
        messageSource.setParentMessageSource(commonMessageSource);
        return messageSource;
    }
}
