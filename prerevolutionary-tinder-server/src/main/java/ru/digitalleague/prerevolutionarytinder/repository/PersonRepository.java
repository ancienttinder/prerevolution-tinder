package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalleague.prerevolutionarytinder.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
