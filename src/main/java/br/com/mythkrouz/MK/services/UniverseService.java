package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface UniverseService {
    public Universe createUniverse(Universe universe) throws EntityAlreadyExistsException;
    public Universe updateUniverse(Universe universe);
    public void deleteUniverse(Long universeId);
    public Optional<Universe> getUniverseById(Long universeId);
    public List<Universe> getAllUniverses();
    public Optional<Universe> getUniverseByName(String name);
    public List<Universe> getAllUniverseByCreatorId(Long creatorId);

}
