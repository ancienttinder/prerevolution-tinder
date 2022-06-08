package ru.digitalleague.prerevolutionarytinder.api;

import ru.digitalleague.prerevolutionarytinder.model.ChoiceView;

public interface ChoiceService {

    void save(ChoiceView choiceView);

    void delete(ChoiceView choiceView);
}
