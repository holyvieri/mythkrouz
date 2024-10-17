package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Universe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UniverseRepository extends JpaRepository<Universe, Long> {
    public List<Universe> findByCreator_UserId(Long creatorId);

    public Optional<Universe> findUniverseByName(String name);

    public Optional<Universe> findByTerritories_TerritoryId(Long territoryId);

    public Optional<Universe> findByCharacters_CharacterId(Long characterId);
}
