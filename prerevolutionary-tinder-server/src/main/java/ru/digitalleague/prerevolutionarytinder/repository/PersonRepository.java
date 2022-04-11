package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.digitalleague.prerevolutionarytinder.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    String PERSON_BY_USER_ID_QUERY = "select row_number() over () as id, " +
            "p.gender        as gender" +
            "p.name          as name" +
            "p.description   as description" +
            "p.SEARCH_TERM   as searchTerm" +
            "p.USER_ID       as userId" +
            "from tinder.person p" +
            "where p.USER_ID = ?1";

    @Query(value = PERSON_BY_USER_ID_QUERY, nativeQuery = true)
    Person findByUserId(String userId);
}
