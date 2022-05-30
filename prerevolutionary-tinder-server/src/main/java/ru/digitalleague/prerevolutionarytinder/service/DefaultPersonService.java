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
    private final MessageService messageService;

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
        log.info("Save person: {}", person);
        Person oldPerson = personRepository.findByUserId(person.getUserId());
        if (oldPerson != null) {
            person.setId(oldPerson.getId());
        }
        if (person.getName() != null) {
            person.setName(translateSlavonicService.translate(person.getName()));
        }
        if (person.getDescription() != null) {
            person.setDescription(translateSlavonicService.translate(person.getDescription()));
        }
        personRepository.save(person);
        log.info("Successfully save person: {}", person);
        return person;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findSuitablePerson(String userId) {
        log.info("Get person by search term by userId: {}", userId);
        Person person = personRepository.findByUserId(userId);
        if (messageService.getMessage("tinder.gender.sir").equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), messageService.getMessage("tinder.gender.all")),
                    Arrays.asList(messageService.getMessage("tinder.gender.sir")));
        } else if (messageService.getMessage("tinder.gender.madam").equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), messageService.getMessage("tinder.gender.all")),
                    Arrays.asList(messageService.getMessage("tinder.gender.madam")));
        } else if (messageService.getMessage("tinder.gender.all").equals(person.getSearchTerm())) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), messageService.getMessage("tinder.gender.all")),
                    Arrays.asList(messageService.getMessage("tinder.gender.sir"), messageService.getMessage("tinder.gender.madam")));
        }
        return null;
    }

    @Override
    public List<Person> findLikeHistory(String userId) {
        log.info("Get like history by userId: {}", userId);
        List<Person> personLikeToSome = personRepository.findChoicePersonByUserId(userId);
        List<Person> someLikeToPerson = personRepository.findChoiceSelectedByUserId(userId);

        List<Person> mutualityLikes = personLikeToSome.stream()
                .filter(someLikeToPerson::contains)
                .collect(Collectors.toList());
        personLikeToSome.removeAll(mutualityLikes);
        someLikeToPerson.removeAll(mutualityLikes);

        addMutualityToName(personLikeToSome, messageService.getMessage("tinder.type.mutuality.likeyou"));
        addMutualityToName(someLikeToPerson, messageService.getMessage("tinder.type.mutuality.youliked"));
        addMutualityToName(mutualityLikes, messageService.getMessage("tinder.type.mutuality.both"));

        List<Person> likeHistory = new ArrayList<>();
        likeHistory.addAll(personLikeToSome);
        likeHistory.addAll(someLikeToPerson);
        likeHistory.addAll(mutualityLikes);
        return likeHistory;
    }

    private void addMutualityToName(List<Person> persons, String textToAdd) {
        persons.forEach(person -> person.setName(person.getName() + ", " + textToAdd));
    }
}
