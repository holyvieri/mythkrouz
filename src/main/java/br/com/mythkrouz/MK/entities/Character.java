package br.com.mythkrouz.MK.entities;

import br.com.mythkrouz.MK.entities.enums.CharacterClass;
import br.com.mythkrouz.MK.entities.enums.Gender;
import br.com.mythkrouz.MK.entities.enums.Race;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor TODO: TIRAR ESSES E COLOCAR O CONSTRUTOR DE DTO
//@AllArgsConstructor TODO: FZR IMAGEM DO BD, TIRAR DUVIDAS ELISSON E FZR DTOS
@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Race race;
    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;
    private String description;


    @ManyToOne
    @JoinColumn(name = "territory_id", nullable = false)
    private Territory territory;


    @ManyToMany
    @JoinTable(
            name="character_event",
            joinColumns=@JoinColumn(name="character_id"),
            inverseJoinColumns =@JoinColumn(name="event_id")
    )
    private List<Event> events;


}
