package ru.digitalleague.client.api;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.BotState;

import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(Person person, String message);

    BotState operatedBotState();

    List<String> operatedCallBackQuery();
}
