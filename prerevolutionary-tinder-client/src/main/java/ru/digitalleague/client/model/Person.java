package ru.digitalleague.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digitalleague.client.type.BotState;
import ru.digitalleague.client.type.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;

    private Gender gender;

    private String name;

    private String description;

    private Gender searchTerm;

    private String userId;

    private BotState botState;
}
