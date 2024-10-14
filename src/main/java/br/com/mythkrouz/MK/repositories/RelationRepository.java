package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {
    public List<Relation> findByCharacter1_IdOrCharacter2(Long characterId1, Long characterId2);
}
