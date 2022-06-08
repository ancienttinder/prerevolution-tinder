package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalleague.prerevolutionarytinder.api.PersonService;
import ru.digitalleague.prerevolutionarytinder.builder.NewspaperPersonBuilder;
import ru.digitalleague.prerevolutionarytinder.builder.PersonBuilder;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.mapper.PersonMapper;
import ru.digitalleague.prerevolutionarytinder.model.NewspaperPersonView;
import ru.digitalleague.prerevolutionarytinder.model.PersonView;
import ru.digitalleague.prerevolutionarytinder.repository.PersonRepository;
import ru.digitalleague.prerevolutionarytinder.type.Gender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final PersonRepository personRepository;
    private final MessageService messageService;
    private final NewspaperPersonBuilder newspaperPersonBuilder;
    private final PersonMapper personMapper;
    private final PersonBuilder personBuilder;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.info("Get all persons");
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonView findByUserId(String userId) {
        log.info("Get person by userId: {}", userId);
        return personMapper.toView(personRepository.findPersonByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public NewspaperPersonView findPhotoByUserId(String userId) {
        log.info("Get person by userId: {}", userId);
        Person person = personRepository.findPersonByUserId(userId);
        return newspaperPersonBuilder.build(person);
    }

    @Override
    @Transactional
    public PersonView save(PersonView person) {
        log.info("Save person: {}", person);
        Person oldPerson = personRepository.findPersonByUserId(person.getUserId());
        Person newPerson = personBuilder.build(person, oldPerson);
        personRepository.save(newPerson);
        log.info("Successfully save person: {}", newPerson);
        return personMapper.toView(newPerson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewspaperPersonView> findPhotoSuitablePerson(String userId) {
        log.info("Get person by search term by userId: {}", userId);
        Person person = personRepository.findPersonByUserId(userId);
        List<Person> suitablePersons = getSuitablePerson(person);
        return suitablePersons.stream()
                .map(newspaperPersonBuilder::build)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewspaperPersonView> findPhotoLikeHistory(String userId) {
        log.info("Get like history by userId: {}", userId);
        List<Person> personLikeToSome = personRepository.findChoicePersonByUserId(userId);
        List<Person> someLikeToPerson = personRepository.findChoiceSelectedByUserId(userId);
        List<Person> likeHistory = getLikeHistory(personLikeToSome, someLikeToPerson);
        return likeHistory.stream()
                .map(newspaperPersonBuilder::build)
                .collect(Collectors.toList());
    }

    private void addMutualityToName(List<Person> persons, String textToAdd) {
        persons.forEach(person -> person.setName(person.getName() + ", " + textToAdd));
    }

    private List<Person> getSuitablePerson(Person person) {
        if (Gender.SIR.equals(Gender.valueOf(person.getSearchTerm()))) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), Gender.ALL.toString()),
                    Collections.singletonList(Gender.SIR.toString()));
        } else if (Gender.MADAM.equals(Gender.valueOf(person.getSearchTerm()))) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), Gender.ALL.toString()),
                    Collections.singletonList(Gender.MADAM.toString()));
        } else if (Gender.ALL.equals(Gender.valueOf(person.getSearchTerm()))) {
            return personRepository.findPersonBySearchTermInAndGenderIn(
                    Arrays.asList(person.getGender(), Gender.ALL.toString()),
                    Arrays.asList(Gender.SIR.toString(), Gender.MADAM.toString()));
        }
        return Collections.emptyList();
    }

    private List<Person> getLikeHistory(List<Person> personLikeToSome, List<Person> someLikeToPerson) {
        List<Person> mutualityLikes = personLikeToSome.stream()
                .filter(someLikeToPerson::contains)
                .collect(Collectors.toList());
        personLikeToSome.removeAll(mutualityLikes);
        someLikeToPerson.removeAll(mutualityLikes);

        addMutualityToName(personLikeToSome,
                messageService.getMessage("tinder.type.mutuality.likeyou"));
        addMutualityToName(someLikeToPerson,
                messageService.getMessage("tinder.type.mutuality.youliked"));
        addMutualityToName(mutualityLikes,
                messageService.getMessage("tinder.type.mutuality.both"));

        List<Person> likeHistory = new ArrayList<>();
        likeHistory.addAll(personLikeToSome);
        likeHistory.addAll(someLikeToPerson);
        likeHistory.addAll(mutualityLikes);

        return likeHistory;
    }
}
