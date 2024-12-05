package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {

    public List<Relation> findAllByCharacter1_CharacterIdOrCharacter2_CharacterId(Long characterId1, Long characterId2);
    public List<Relation> findAllByRelationType(RelationType relationType);

    @Query("SELECT r FROM Relation r WHERE r.character1.characterId = :character1Id " +
            "AND r.character2.characterId = :character2Id " +
            "AND r.relationType = :relationType")
    public Optional<Relation> findByCharactersAndType(@Param("character1Id") Long character1Id,
                                               @Param("character2Id") Long character2Id,
                                               @Param("relationType") RelationType relationType);

}
