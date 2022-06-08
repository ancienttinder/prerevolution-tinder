package ru.digitalleague.prerevolutionarytinder.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "choices", schema = "tinder")
@SequenceGenerator(schema = "tinder", name = "choices_s", sequenceName = "tinder.choices_s", allocationSize = 1)
public class Choice {

    @EmbeddedId
    private ChoiceKey key;
}