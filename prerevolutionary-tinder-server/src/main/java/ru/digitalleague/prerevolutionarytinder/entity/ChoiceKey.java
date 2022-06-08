package ru.digitalleague.prerevolutionarytinder.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class ChoiceKey implements Serializable {
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "selected_id")
    private Long selectedId;
}
