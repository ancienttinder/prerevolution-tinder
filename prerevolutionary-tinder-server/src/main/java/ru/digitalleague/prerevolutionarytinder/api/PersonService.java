package ru.digitalleague.prerevolutionarytinder.api;

import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.model.NewspaperPersonView;
import ru.digitalleague.prerevolutionarytinder.model.PersonView;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    PersonView findByUserId(String userId);

    NewspaperPersonView findPhotoByUserId(String userId);

    PersonView save(PersonView person);

    List<NewspaperPersonView> findPhotoSuitablePerson(String userId);

    List<NewspaperPersonView> findPhotoLikeHistory(String userId);
}
