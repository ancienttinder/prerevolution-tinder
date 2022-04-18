package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.digitalleague.prerevolutionarytinder.entity.Person;

import java.util.Collection;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    String PERSON_BY_USER_ID_QUERY = "select row_number() over () as id, " +
            "p.gender        as gender, " +
            "p.name          as name, " +
            "p.description   as description, " +
            "p.SEARCH_TERM   as searchTerm, " +
            "p.USER_ID       as userId " +
            "from tinder.persons p " +
            "where p.USER_ID = ?1 ";

    String CHOICE_PERSON_BY_USER_ID_QUERY = "select row_number() over () as id, " +
            "ps.gender        as gender, " +
            "ps.name          as name, " +
            "ps.description   as description, " +
            "ps.SEARCH_TERM   as searchTerm, " +
            "ps.USER_ID       as userId " +
            "from tinder.persons p" +
            "   join tinder.choice c on p.id = c.person_id " +
            "   join tinder.persons ps on c.selected_id = ps.id " +
            "where p.USER_ID = ?1 ";

    String CHOICE_SELECTED_BY_USER_ID_QUERY = "select row_number() over () as id, " +
            "ps.gender        as gender, " +
            "ps.name          as name, " +
            "ps.description   as description, " +
            "ps.SEARCH_TERM   as searchTerm, " +
            "ps.USER_ID       as userId " +
            "from tinder.persons p" +
            "   join tinder.choice c on p.id = c.selected_id " +
            "   join tinder.persons ps on c.person_id = ps.id " +
            "where p.USER_ID = ?1 ";

    String PERSON_BY_SEARCH_TERM_QUERY = "select row_number() over () as id, " +
            "p.gender        as gender, " +
            "p.name          as name, " +
            "p.description   as description, " +
            "p.SEARCH_TERM   as searchTerm, " +
            "p.USER_ID       as userId " +
            "from tinder.persons p " +
            "where p.SEARCH_TERM IN ?1 AND p.gender IN ?2 ";

    @Query(value = PERSON_BY_USER_ID_QUERY, nativeQuery = true)
    Person findByUserId(String userId);

    //todo альтернатива
//    Person findByUserId(String userId);

    @Query(value = CHOICE_PERSON_BY_USER_ID_QUERY, nativeQuery = true)
    List<Person> findChoicePersonByUserId(String userId);

    @Query(value = CHOICE_SELECTED_BY_USER_ID_QUERY, nativeQuery = true)
    List<Person> findChoiceSelectedByUserId(String userId);

    //todo альтернатива spring data
    @Query(value = PERSON_BY_SEARCH_TERM_QUERY, nativeQuery = true)
    List<Person> findPersonBySearchTerm(Collection<String> searchTerm, Collection<String> gender);
}
