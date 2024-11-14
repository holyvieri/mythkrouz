package br.com.mythkrouz.MK.entities;

import br.com.mythkrouz.MK.entities.enums.Rarity;
import br.com.mythkrouz.MK.entities.enums.Type;
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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "territory_id")
    private Territory origin;

    @ManyToMany
    @JoinColumn(name = "owners_ids")
    private List<Character> owners;
}
