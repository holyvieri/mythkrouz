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
public class Territory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long territoryId;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "universe_id", nullable = false)
    private Universe universe;

    @ManyToMany
    @JoinTable(
            name = "territory_event",
            joinColumns = @JoinColumn(name = "territory_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> relatedEvents;

    @ManyToMany
    @JoinTable(
            name = "territory_item",
            joinColumns = @JoinColumn(name = "territory_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> relatedItems;



}
