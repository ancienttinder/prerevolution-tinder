package ru.digitalleague.prerevolutionarytinder.api;

import ru.digitalleague.prerevolutionarytinder.entity.Person;

import java.util.List;

public interface PersonService {

    List<Person> findAll();
}
