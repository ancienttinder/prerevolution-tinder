package ru.digitalleague.prerevolutionarytinder.api;

import ru.digitalleague.prerevolutionarytinder.entity.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();

    Person findByUserId(String userId);

    Person save(Person person);

    List<Person> findSuitablePerson(String userId);

    List<Person> findLikeHistory(String userId);
}
