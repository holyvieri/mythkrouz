package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.TerritoryDTO;
import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface TerritoryService {
    public TerritoryDTO createTerritory(Territory territory, String userEmail) throws EntityAlreadyExistsException;
    public Territory updateTerritory(Territory territory, String userEmail);
    public void deleteTerritory(Long territoryId, String userEmail);
    public Optional<Territory> getTerritoryById(Long territoryId, String userEmail);
    public List<Territory> getAllTerritory();
    public Optional<Territory> getTerritoryByName(String territoryName);
    public List<Territory> getAllTerritoriesByUniverse(Long universeId);
}
