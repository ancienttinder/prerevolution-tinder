package ru.digitalleague.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.digitalleague.client.api.RestServerExchanger;
import ru.digitalleague.client.model.Choice;
import ru.digitalleague.client.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRestServerExchanger implements RestServerExchanger {

    private final RestTemplate restTemplate;

    @Override
    public Person getPersonByUserId(String id) {
        log.info("Get person by userId {}", id);
        HttpEntity<String> entity = getHttpEntity();
        String url = "/persons/find/person/" + id;
        Person person = null;
        try {
            ResponseEntity<Person> response = restTemplate.exchange(url, HttpMethod.GET, entity, Person.class);
            person = response.getBody();
        } catch (RestClientException e)  {
            log.error("Error get data with id {}", id, e);
        }
        return person;
    }

    @Override
    public List<Person> getLikeHistory(String id) {
        log.info("Get like history by userId {}", id);
        String url = "/persons/find/history/" + id;
        try {
            Person[] persons = restTemplate.getForEntity(url, Person[].class).getBody();
            log.info("Get like history: {}",persons.toString());
            return Arrays.asList(persons);
        } catch (RestClientException e) {
            log.error("Error get data", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Person save(Person person) {
        log.info("Save person: {}",person);
        String url = "/persons/save/";
        try {
            Person response = restTemplate.postForEntity(url, person, Person.class).getBody();
            log.info("Save person: {}", response);
            return person;
        } catch (RestClientException e) {
            log.error("Error save", e);
        }
        return null;
    }

    @Override
    public void saveChoice(Choice choice) {
        log.info("Save choice: {}", choice);
        String url = "/choices/save/";
        try {
            restTemplate.postForEntity(url, choice, Choice.class);
            log.info("Save choice: {}",choice);
        } catch (RestClientException e) {
            log.error("Error save", e);
        }
    }

    @Override
    public void deleteChoice(Choice choice) {
        log.info("Delete choice: {}", choice);
        String url = "/choices/delete/";
        try {
            restTemplate.postForEntity(url, choice, Choice.class);
            log.info("Delete choice: {}",choice);
        } catch (RestClientException e) {
            log.error("Error delete", e);
        }
    }

    @Override
    public List<Person> getSuitablePerson(String id) {
        log.info("Get suitable person by userId: {}", id);
        String url = "/persons/find/suitable/persons/" + id;
        try {
            Person[] persons = restTemplate.getForEntity(url, Person[].class).getBody();
            log.info("Get suitable person: {}",persons.toString());
            return Arrays.asList(persons);
        } catch (RestClientException e) {
            log.error("Error get data", e);
        }
        return Collections.emptyList();
    }


    private HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(httpHeaders);
    }

}
