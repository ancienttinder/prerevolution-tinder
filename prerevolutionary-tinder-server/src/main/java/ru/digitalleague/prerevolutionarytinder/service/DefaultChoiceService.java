package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalleague.prerevolutionarytinder.api.ChoiceService;
import ru.digitalleague.prerevolutionarytinder.mapper.ChoiceMapper;
import ru.digitalleague.prerevolutionarytinder.model.ChoiceView;
import ru.digitalleague.prerevolutionarytinder.repository.ChoiceRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultChoiceService implements ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final ChoiceMapper choiceMapper;

    @Override
    @Transactional
    public void save(ChoiceView choiceView) {
        log.info("Save choice: {}", choiceView);
        choiceRepository.save(choiceMapper.toEntity(choiceView));
        log.info("Successfully saved choice: {}", choiceView);
    }

    @Override
    @Transactional
    public void delete(ChoiceView choiceView) {
        log.info("Delete choice: {}", choiceView);
        choiceRepository.delete(choiceMapper.toEntity(choiceView));
        log.info("Successfully delete choice: {}", choiceView);
    }

}
