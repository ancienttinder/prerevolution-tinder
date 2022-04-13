package ru.digitalleague.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.prerevolutionarytinder.api.PersonService;
import ru.digitalleague.prerevolutionarytinder.entity.Person;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/find/person/{userId}")
    public Person findPersonByUserId(@PathVariable String userId) {
        return personService.findByUserId(userId);
    }

    @GetMapping("/find/choice/{userId}")
    public Person findChoicePersonByUserId(@PathVariable String userId) {
        return personService.findByUserId(userId);
    }

    @GetMapping("/search/persons/{userId}")
    public List<Person> searchPersonByUserId(@PathVariable String userId) {
        return personService.searchPerson(userId);
    }

    @GetMapping("/find/history/{userId}")
    public List<Person> findLikeHistoryByUserId(@PathVariable String userId) {
        return personService.findLikeHistory(userId);
    }
}
