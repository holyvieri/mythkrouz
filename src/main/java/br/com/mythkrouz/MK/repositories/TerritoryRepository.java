package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Territory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerritoryRepository extends JpaRepository<Territory, Long> {
    public List<Territory> findByUniverseId(Long universeId);
    public List<Territory> findByRelatedEvents(Long eventId);
    public List<Territory> findByRelatedItems(Long itemId);
}
