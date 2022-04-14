package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;

import java.util.Collection;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

    String CHOICE_BY_PERSON_AND_SELECTED_ID_QUERY = "select row_number() over () as id, " +
            "c.PERSON_ID        as personId," +
            "c.SELECTED_ID          as selectedId" +
            "from tinder.choices c" +
            "where c.PERSON_ID = ?1" +
            "   AND c.SELECTED_ID = ?2";

    @Query(value = CHOICE_BY_PERSON_AND_SELECTED_ID_QUERY, nativeQuery = true)
    Choice findByPersonAndSelectedId(String personId, String selectedId);

    @Modifying
    @Query(value = "delete from fccr.choices c WHERE c.id = ?1", nativeQuery = true)
    void deleteChoiceById(Integer id);
}
