package ru.digitalleague.prerevolutionarytinder.api;

import ru.digitalleague.prerevolutionarytinder.entity.Choice;

public interface ChoiceService {

    void save(Choice choice);

    void delete(Choice choice);
}
