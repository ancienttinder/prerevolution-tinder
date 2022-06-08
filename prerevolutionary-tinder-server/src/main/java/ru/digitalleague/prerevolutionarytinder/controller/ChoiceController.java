package ru.digitalleague.prerevolutionarytinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.prerevolutionarytinder.api.ChoiceService;
import ru.digitalleague.prerevolutionarytinder.model.ChoiceView;

@RestController
@RequestMapping("/choices")
@RequiredArgsConstructor
public class ChoiceController {

    private final ChoiceService choiceService;

    @PostMapping("/save")
    public void saveChoice(@RequestBody ChoiceView choiceView) {
        choiceService.save(choiceView);
    }

    @PostMapping("/delete")
    public void deleteChoice(@RequestBody ChoiceView choiceView) {
        choiceService.delete(choiceView);
    }
}
