package ru.digitalleague.client.api;

import ru.digitalleague.client.model.Choice;
import ru.digitalleague.client.model.NewspaperPerson;
import ru.digitalleague.client.model.Person;

import java.util.List;

public interface RestServerExchanger {

    Person getPersonByUserId(String id);

    NewspaperPerson getPhotoPersonByUserId(String id);

    List<NewspaperPerson> getLikeHistory(String id);

    Person save(Person person);

    void saveChoice(Choice choice);

    void deleteChoice(Choice choice);

    List<NewspaperPerson> getSuitablePerson(String id);
}
