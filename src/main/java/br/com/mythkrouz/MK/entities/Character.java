package br.com.mythkrouz.MK.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String race;
    private String characterClass;
    private String gender;
    private int age;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "character_item",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @OneToMany(mappedBy = "character1")
    private List<Relation> relations;

    @ManyToOne
    @JoinColumn(name = "universe_id", nullable = false)
    private Universe universe;


}
