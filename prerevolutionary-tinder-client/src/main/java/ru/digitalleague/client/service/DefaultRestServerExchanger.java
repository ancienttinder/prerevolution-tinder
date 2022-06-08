package ru.digitalleague.client.service;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Choice;
import ru.digitalleague.client.model.NewspaperPerson;
import ru.digitalleague.client.model.Person;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRestServerExchanger implements RestServerExchanger {

    private final RestTemplate restTemplate;

    @Override
    public Person getPersonByUserId(String id) {
        log.info("Get person by userId {}", id);
        String url = "/persons/find/person/" + id;
        return Try.of(() -> restTemplate.getForEntity(url, Person.class).getBody())
                .onSuccess(person -> log.info("Get person by userId: {}", person))
                .onFailure(throwable -> log.error("Error get data with id {}", id, throwable))
                .recover(throwable -> null)
                .get();
    }

    @Override
    public NewspaperPerson getPhotoPersonByUserId(String id) {
        log.info("Get person by userId {}", id);
        String url = "/persons/find/photo/person/" + id;
        return Try.of(() -> restTemplate.getForEntity(url, NewspaperPerson.class).getBody())
                .onSuccess(newspaperPerson -> log.info("Get person by userId: {}", newspaperPerson))
                .onFailure(throwable -> log.error("Error get data with id {}", id, throwable))
                .recover(throwable -> null)
                .get();
    }

    @Override
    public List<NewspaperPerson> getLikeHistory(String id) {
        log.info("Get like history by userId {}", id);
        String url = "/persons/find/photo/history/" + id;
        NewspaperPerson[] persons = Try.of(() -> restTemplate.getForEntity(url, NewspaperPerson[].class).getBody())
                .onSuccess(newspaperPerson -> log.info("Get person by userId: {}", (Object) newspaperPerson))
                .onFailure(throwable -> log.error("Error get data with id {}", id, throwable))
                .recover(throwable -> null)
                .get();
        return Arrays.asList(persons);
    }

    @Override
    public Person save(Person person) {
        log.info("Save person: {}", person);
        String url = "/persons/save/";
        return Try.of(() -> restTemplate.postForEntity(url, person, Person.class).getBody())
                .onSuccess(p -> log.info("Save person: {}", p))
                .onFailure(throwable -> log.error("Error save {}", person, throwable))
                .recover(throwable -> null)
                .get();
    }

    @Override
    public void saveChoice(Choice choice) {
        log.info("Save choice: {}", choice);
        String url = "/choices/save/";
        Try.of(() -> restTemplate.postForEntity(url, choice, Choice.class))
                .onSuccess(c -> log.info("Save choice: {}", choice))
                .onFailure(throwable -> log.error("Error save {}", choice, throwable))
                .recover(throwable -> null)
                .get();
    }

    @Override
    public void deleteChoice(Choice choice) {
        log.info("Delete choice: {}", choice);
        String url = "/choices/delete/";
        Try.of(() -> restTemplate.postForEntity(url, choice, Choice.class))
                .onSuccess(c -> log.info("Delete choice: {}", choice))
                .onFailure(throwable -> log.error("Error delete {}", choice, throwable))
                .recover(throwable -> null)
                .get();
    }

    @Override
    public List<NewspaperPerson> getSuitablePerson(String id) {
        log.info("Get suitable person by userId: {}", id);
        String url = "/persons/find/photo/suitable/persons/" + id;
        NewspaperPerson[] persons = Try.of(() -> restTemplate.getForEntity(url, NewspaperPerson[].class).getBody())
                .onSuccess(newspaperPerson -> log.info("Get suitable person: {}", (Object) newspaperPerson))
                .onFailure(throwable -> log.error("Error get suitable person {}", id, throwable))
                .recover(throwable -> null)
                .get();
        return Arrays.asList(persons);
    }
}
