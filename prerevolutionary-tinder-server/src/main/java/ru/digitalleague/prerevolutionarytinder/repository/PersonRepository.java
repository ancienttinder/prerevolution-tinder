package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.digitalleague.prerevolutionarytinder.entity.Person;

import java.util.Collection;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    String CHOICE_PERSON_BY_USER_ID_QUERY = "select ps.* " +
            "from tinder.persons p" +
            "   join tinder.choices c on p.id = c.person_id " +
            "   join tinder.persons ps on c.selected_id = ps.id " +
            "where p.USER_ID = ?1 ";

    String CHOICE_SELECTED_BY_USER_ID_QUERY = "select ps.* " +
            "from tinder.persons p" +
            "   join tinder.choices c on p.id = c.selected_id " +
            "   join tinder.persons ps on c.person_id = ps.id " +
            "where p.USER_ID = ?1 ";

    Person findByUserId(String userId);

    @Query(value = CHOICE_PERSON_BY_USER_ID_QUERY, nativeQuery = true)
    List<Person> findChoicePersonByUserId(String userId);

    @Query(value = CHOICE_SELECTED_BY_USER_ID_QUERY, nativeQuery = true)
    List<Person> findChoiceSelectedByUserId(String userId);

    List<Person> findPersonBySearchTermInAndGenderIn(Collection<String> searchTerm, Collection<String> gender);
}
