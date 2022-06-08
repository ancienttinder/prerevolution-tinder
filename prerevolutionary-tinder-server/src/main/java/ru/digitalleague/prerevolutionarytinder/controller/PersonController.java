package ru.digitalleague.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.prerevolutionarytinder.api.PersonService;
import ru.digitalleague.prerevolutionarytinder.entity.Person;
import ru.digitalleague.prerevolutionarytinder.model.NewspaperPersonView;
import ru.digitalleague.prerevolutionarytinder.model.PersonView;

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
    public PersonView findPersonByUserId(@PathVariable String userId) {
        return personService.findByUserId(userId);
    }

    @GetMapping("/find/photo/person/{userId}")
    public NewspaperPersonView findPhotoPersonByUserId(@PathVariable String userId) {
        return personService.findPhotoByUserId(userId);
    }

    @GetMapping("/find/photo/suitable/persons/{userId}")
    public List<NewspaperPersonView> findPhotoSuitablePersonByUserId(@PathVariable String userId) {
        return personService.findPhotoSuitablePerson(userId);
    }

    @GetMapping("/find/photo/history/{userId}")
    public List<NewspaperPersonView> findPhotoLikeHistoryByUserId(@PathVariable String userId) {
        return personService.findPhotoLikeHistory(userId);
    }

    @PostMapping("/save")
    public PersonView savePerson(@RequestBody PersonView person) {
        return personService.save(person);
    }
}
