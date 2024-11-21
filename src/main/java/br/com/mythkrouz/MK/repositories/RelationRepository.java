package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {

    public List<Relation> findAllByCharacter1_CharacterIdOrCharacter2_CharacterId(Long characterId1, Long characterId2);
    public List<Relation> findAllByRelationType(RelationType relationType);
}
