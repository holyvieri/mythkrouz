package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.RelationDTO;
import br.com.mythkrouz.MK.entities.Relation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RelationMapper {
    public static RelationDTO toDTO(Relation relation) {
        if (relation == null) return null;

        return new RelationDTO(
                relation.getRelationId(),
                relation.getCharacter1().getCharacterId(),
                relation.getCharacter2().getCharacterId(),
                relation.getRelationType()
        );
    }

    public static Relation toEntity(RelationDTO relationDTO) {
        if (relationDTO == null) return null;

        Relation relation = new Relation();
        relation.setRelationId(relation.getRelationId());
        relation.setRelationType(relation.getRelationType());

        //todo: chracters no service

        return relation;
    }
}
