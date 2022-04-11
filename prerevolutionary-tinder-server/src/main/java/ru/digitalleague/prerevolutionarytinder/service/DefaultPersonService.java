package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalleague.prerevolutionarytinder.api.PersonService;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.repository.PersonRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findByUserId(String userId) {
        return personRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Person save(Person person){
        Person oldPerson = personRepository.findByUserId(person.getUserId());
        if (oldPerson != null) {
            person.setId(oldPerson.getId());
        }
        personRepository.save(person);
        return person;
    }
}
