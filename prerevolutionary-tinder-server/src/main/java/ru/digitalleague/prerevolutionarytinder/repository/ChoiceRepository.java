package ru.digitalleague.prerevolutionarytinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

}
