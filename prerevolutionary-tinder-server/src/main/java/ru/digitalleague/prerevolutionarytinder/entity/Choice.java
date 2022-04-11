package ru.digitalleague.prerevolutionarytinder.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "choices", schema = "tinder")
@SequenceGenerator(schema = "tinder", name = "choices_s", sequenceName = "tinder.choices_s", allocationSize = 1)
public class Choice {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "choices_s")
    private Integer id;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "selected_id")
    private String selectedId;
}