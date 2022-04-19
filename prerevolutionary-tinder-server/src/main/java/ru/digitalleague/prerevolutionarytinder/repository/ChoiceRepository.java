package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;

import java.util.Optional;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

    Optional<Choice> findChoiceByPersonIdAndSelectedId(String personId, String selectedId);

}
