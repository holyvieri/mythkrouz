package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
    public List<Relation> findByCharacter1_CharacterIdOrCharacter2_CharacterId(Long characterId1, Long characterId2);
}
