package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface TerritoryService {
    public Territory createTerritory(Territory territory) throws EntityAlreadyExistsException;
    public Territory updateTerritory(Territory territory);
    public void deleteTerritory(Long territoryId);
    public Optional<Territory> getTerritoryById(Long territoryId);
    public List<Territory> getAllTerritory();
    public Optional<Territory> getTerritoryByName(String territoryName);
    public List<Territory> getTerritoriesByUniverse(Long universeId);
    public List<Territory> getTerritoriesByRelatedEvents(Long eventId);
    public List<Territory> getTerritoriesByRelatedItems(Long itemId);
}
