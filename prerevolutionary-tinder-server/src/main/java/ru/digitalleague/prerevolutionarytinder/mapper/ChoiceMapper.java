package ru.digitalleague.prerevolutionarytinder.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;
import ru.digitalleague.prerevolutionarytinder.entity.ChoiceKey;
import ru.digitalleague.prerevolutionarytinder.model.ChoiceView;

@Component
@RequiredArgsConstructor
public class ChoiceMapper {
    public Choice toEntity(ChoiceView choiceView) {
        if (choiceView == null) return null;
        return new Choice(
                new ChoiceKey(
                        choiceView.getPersonId(),
                        choiceView.getSelectedId()));
    }
}
