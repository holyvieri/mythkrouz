package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.UpdateUniverseDTO;
import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface UniverseService {
    public Universe createUniverse(Universe universe, String userEmail) throws EntityAlreadyExistsException;
    public Universe updateUniverse(UpdateUniverseDTO universedto, Long universeId, String userEmail);
    public void deleteUniverse(Long universeId, String userEmail);
    public Optional<Universe> getUniverseById(Long universeId, String userEmail);
    public List<Universe> getAllUniverses();
    public Optional<Universe> getUniverseByName(String name);
    public List<Universe> getAllUniverseByCreatorId(Long creatorId);

}
