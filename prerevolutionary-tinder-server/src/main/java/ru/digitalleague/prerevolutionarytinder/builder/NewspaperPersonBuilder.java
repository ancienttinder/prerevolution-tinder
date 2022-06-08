package ru.digitalleague.prerevolutionarytinder.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.model.NewspaperPersonView;
import ru.digitalleague.prerevolutionarytinder.service.MessageService;
import ru.digitalleague.prerevolutionarytinder.type.Gender;

import java.io.File;

@Component
@RequiredArgsConstructor
public class NewspaperPersonBuilder {
    private final MessageService messageService;

    public NewspaperPersonView build(Person person) {
        return new NewspaperPersonView(
                new File(person.getImagePath()),
                messageService.getMessage(Gender.valueOf(person.getGender()).getMessageId()) + ", " + person.getName(),
                (long) person.getId());
    }
}
