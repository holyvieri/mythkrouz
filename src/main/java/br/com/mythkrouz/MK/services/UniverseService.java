package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface UniverseService {
    public Universe createUniverse() throws EntityAlreadyExistsException;
    public Optional<Universe> getUniverseById(Long id);
    public Universe updateUniverse(Universe universe);
    public void deleteUniverse(Long id);
    public List<Universe> getAllUniverses();
    public Optional<Universe> getUniverseByName(String name);
    public List<Universe> getUniverseByCreatorId(Long creatorId);
    public Optional<Universe> getUniverseByTerritoryId(Long territoryId);
    public Optional<Universe> getUniverseByCharacterId(Long characterId);

}
