package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, Long> {
    public Optional<Territory> findByName(String territoryName);
    public List<Territory> findByUniverse_UniverseId(Long universeId);
    public List<Territory> findByRelatedEvents_EventId(Long eventId);
    public List<Territory> findByRelatedItems_ItemId(Long itemId);
}
