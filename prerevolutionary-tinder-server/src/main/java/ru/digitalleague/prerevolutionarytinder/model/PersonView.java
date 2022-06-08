package ru.digitalleague.prerevolutionarytinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digitalleague.prerevolutionarytinder.type.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonView {
    private Integer id;
    private Gender gender;
    private String name;
    private String description;
    private Gender searchTerm;
    private String userId;
    private String botState;
}
