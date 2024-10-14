package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Territory;

import java.util.List;
import java.util.Optional;

public interface TerritoryService {
    public Territory createTerritory(Territory territory) throws Exception;
    public Territory updateTerritory(Territory territory);
    public void deleteTerritory(Long id);
    public Optional<Territory> getTerritoryById(Long id);
    public List<Territory> getAllTerritory();
    public Optional<Territory> getTerritoryByName(String territoryName);
    public List<Territory> getTerritoriesByUniverse(Long universeId);
    public List<Territory> getTerritoriesByRelatedEvents(Long eventId);
    public List<Territory> getTerritoriesByRelatedItems(Long itemId);
}
