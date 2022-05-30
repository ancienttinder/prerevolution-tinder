package ru.digitalleague.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digitalleague.client.type.BotState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;

    private String gender;

    private String name;

    private String description;

    private String searchTerm;

    private String userId;

    private BotState botState;

}
