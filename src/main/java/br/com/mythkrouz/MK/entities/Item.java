package br.com.mythkrouz.MK.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    private String description;
    private String type;
    private String rarity;

    @ManyToOne
    @JoinColumn(name = "universe_id")
    private Universe universe;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Character owner;
}
