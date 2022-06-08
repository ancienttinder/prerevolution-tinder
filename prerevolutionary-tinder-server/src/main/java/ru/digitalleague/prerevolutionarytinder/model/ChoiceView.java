package ru.digitalleague.prerevolutionarytinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceView {
    private Long personId;
    private Long selectedId;
}
