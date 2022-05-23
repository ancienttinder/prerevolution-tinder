package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalleague.prerevolutionarytinder.api.ChoiceService;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;
import ru.digitalleague.prerevolutionarytinder.repository.ChoiceRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultChoiceService implements ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Override
    @Transactional
    public void save(Choice choice) {
        log.info("Save choice: {}", choice);
        Optional<Choice> existingChoice = choiceRepository.findChoiceByPersonIdAndSelectedId(choice.getPersonId(), choice.getSelectedId());
        if (!existingChoice.isPresent()) choiceRepository.save(choice);
        log.info("Successfully saved choice: {}", choice);
    }

    @Override
    @Transactional
    public void delete(Choice choice) {
        log.info("Delete choice: {}", choice);
        Optional<Choice> existingChoice = choiceRepository.findChoiceByPersonIdAndSelectedId(choice.getPersonId(), choice.getSelectedId());
        existingChoice.ifPresent(value -> choiceRepository.deleteById(value.getId()));
        log.info("Successfully delete choice: {}", choice);
    }

}
