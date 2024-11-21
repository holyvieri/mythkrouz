package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, Long> {
    public Optional<Territory> findByName(String territoryName);
    public List<Territory> findAllByUniverseId(Long universeId);
}
