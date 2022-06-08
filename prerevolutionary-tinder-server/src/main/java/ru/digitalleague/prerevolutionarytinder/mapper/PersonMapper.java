package ru.digitalleague.prerevolutionarytinder.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.model.PersonView;
import ru.digitalleague.prerevolutionarytinder.type.Gender;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    public PersonView toView(Person person) {
        if (person == null) return null;
        return new PersonView(
                person.getId(),
                person.getGender() == null ? null : Gender.valueOf(person.getGender()),
                person.getName(),
                person.getDescription(),
                person.getSearchTerm() == null ? null : Gender.valueOf(person.getSearchTerm()),
                person.getUserId(),
                person.getBotState());
    }
}
