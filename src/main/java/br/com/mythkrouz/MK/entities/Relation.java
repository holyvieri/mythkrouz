package br.com.mythkrouz.MK.entities;

import br.com.mythkrouz.MK.entities.enums.RelationType;
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
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    @ManyToOne
    @JoinColumn(name = "character1_id", nullable = false)
    private Character character1;

    @ManyToOne
    @JoinColumn(name = "character2_id", nullable = false)
    private Character character2;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;
}
