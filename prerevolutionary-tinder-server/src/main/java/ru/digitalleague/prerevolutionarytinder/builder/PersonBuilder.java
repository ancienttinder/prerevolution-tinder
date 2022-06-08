package ru.digitalleague.prerevolutionarytinder.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.api.NewspaperImageService;
import ru.digitalleague.prerevolutionarytinder.api.TranslateSlavonicService;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.model.PersonView;

@Component
@RequiredArgsConstructor
public class PersonBuilder {
    private final TranslateSlavonicService translateSlavonicService;
    private final NewspaperImageService newspaperImageService;

    public Person build(PersonView person, Person oldPerson) {
        return new Person(
                oldPerson == null ? null : oldPerson.getId(),
                person.getGender() == null ? null : person.getGender().toString(),
                person.getName() == null ? null : translateSlavonicService.translateRusToSlavonic(person.getName()),
                person.getDescription() == null ? null : translateSlavonicService.translateRusToSlavonic(person.getDescription()),
                person.getSearchTerm() == null ? null : person.getSearchTerm().toString(),
                person.getUserId() == null ? null : person.getUserId(),
                person.getBotState() == null ? null : person.getBotState(),
                getPathPhoto(person, oldPerson)
        );
    }

    private String getPathPhoto(PersonView person, Person oldPerson) {
        if (oldPerson != null && person.getDescription() != null && translateSlavonicService.translateRusToSlavonic(person.getDescription()).equals(oldPerson.getDescription())) {
            return oldPerson.getImagePath();
        } else {
            if (person.getDescription() != null) {
                return newspaperImageService.getImage(person.getDescription()).toString();
            } else {
                return null;
            }
        }
    }
}
