package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalleague.prerevolutionarytinder.api.PersonService;
import ru.digitalleague.prerevolutionarytinder.api.TranslateSlavonicService;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.repository.PersonRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final PersonRepository personRepository;
    private final TranslateSlavonicService translateSlavonicService;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.info("Get all persons");
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findByUserId(String userId) {
        log.info("Get person by userId: {}", userId);
        return personRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        log.info("Save person");
        Person oldPerson = personRepository.findByUserId(person.getUserId());
        if (oldPerson != null) {
            person.setId(oldPerson.getId());
        }
        person.setName(translateSlavonicService.translate(person.getName()));
        person.setDescription(translateSlavonicService.translate(person.getDescription()));
        personRepository.save(person);
        log.info("Successfully save person: {}", person);
        return person;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> searchPerson(String userId) {
        log.info("Get person by search term by userId: {}", userId);
        Person person = personRepository.findByUserId(userId);
        if ("Сударъ".equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTerm(Arrays.asList(person.getGender(), "Всех"), Arrays.asList("Сударъ"));
        } else if ("Сударыня".equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTerm(Arrays.asList(person.getGender(), "Всех"), Arrays.asList("Сударыня"));
        } else if ("Всех".equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTerm(Arrays.asList(person.getGender(), "Всех"), Arrays.asList("Сударъ", "Сударыня"));
        }
        return null;
    }

    @Override
    public List<Person> findLikeHistory(String userId) {
        log.info("Get like history by userId: {}", userId);
        List<Person> likeHistory = new ArrayList<>();
        List<Person> personLikeToSome = personRepository.findChoicePersonByUserId(userId);
        List<Person> someLikeToPerson = personRepository.findChoiceSelectedByUserId(userId);
        List<Person> mutualityLikes = personLikeToSome.stream()
                .filter(someLikeToPerson::contains)
                .collect(Collectors.toList());
        personLikeToSome.removeAll(mutualityLikes);
        someLikeToPerson.removeAll(mutualityLikes);
        personLikeToSome.stream()
                .forEach(person -> person.setName(person.getName() + ", Любим вами"));
        someLikeToPerson.stream()
                .forEach(person -> person.setName(person.getName() + ", Вы любимы"));
        mutualityLikes.stream()
                .forEach(person -> person.setName(person.getName() + ", Взаимность"));
        likeHistory.addAll(personLikeToSome);
        likeHistory.addAll(someLikeToPerson);
        likeHistory.addAll(mutualityLikes);
        return likeHistory;
    }
}
