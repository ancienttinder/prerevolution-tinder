package ru.digitalleague.prerevolutionarytinder.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons", schema = "tinder")
@SequenceGenerator(schema = "tinder", name = "persons_s", sequenceName = "tinder.persons_s", allocationSize = 1)
public class Person {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persons_s")
    private Integer id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "search_term")
    private String searchTerm;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "bot_state")
    private String botState;
}