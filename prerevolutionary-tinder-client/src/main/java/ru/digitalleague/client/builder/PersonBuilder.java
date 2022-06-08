package ru.digitalleague.client.builder;

import org.springframework.stereotype.Component;
import ru.digitalleague.client.model.Person;
import ru.digitalleague.client.type.BotState;

@Component
public class PersonBuilder {
    public Person build(Person person, BotState botState) {
        return new Person(
                person.getId(),
                person.getGender(),
                person.getName(),
                person.getDescription(),
                person.getSearchTerm(),
                person.getUserId(),
                botState
        );
    }

    public Person startBuild(String userId) {
        return new Person(
                null,
                null,
                null,
                null,
                null,
                userId,
                BotState.START
        );
    }

}
