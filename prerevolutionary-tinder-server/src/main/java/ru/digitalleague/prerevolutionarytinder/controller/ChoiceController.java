package ru.digitalleague.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.prerevolutionarytinder.api.ChoiceService;
import ru.digitalleague.prerevolutionarytinder.entity.Choice;

@RestController
@RequestMapping("/choices")
@RequiredArgsConstructor
public class ChoiceController {

    private final ChoiceService choiceService;

    @PostMapping("/save")
    public void saveChoice(@RequestBody Choice choice) {
        choiceService.save(choice);
    }

    @PostMapping("/delete")
    public void deleteChoice(@RequestBody Choice choice) {
        choiceService.delete(choice);
    }
}
