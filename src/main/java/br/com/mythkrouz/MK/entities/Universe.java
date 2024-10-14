package br.com.mythkrouz.MK.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Universe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "universe")
    private List<Character> characters;

    @OneToMany(mappedBy = "universe")
    private List<Territory> territories;

    @OneToMany(mappedBy = "universe")
    private List<Event> events;

    @OneToMany(mappedBy = "universe")
    private List<Item> items;

}
