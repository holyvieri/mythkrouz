package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.UniverseRepository;
import br.com.mythkrouz.MK.services.UniverseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniverseServiceImpl implements UniverseService {

    private final UniverseRepository universeRepository;

    @Autowired
    public UniverseServiceImpl(UniverseRepository universeRepository) {
        this.universeRepository = universeRepository;
    }

    @Transactional
    @Override
    public Universe createUniverse(Universe universe) throws EntityAlreadyExistsException {
        if (universe.getName() == null || universe.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do Universo não pode ser nulo ou vazio.");
        }

        Optional<Universe> existingUniverse = universeRepository.findByName(universe.getName());
        if (existingUniverse.isPresent()) {
            throw new EntityAlreadyExistsException("Universo");
        }

        return universeRepository.save(universe);
    }


    @Transactional
    @Override
    public Universe updateUniverse(Universe universe) {
        Optional<Universe> existingUniverse = universeRepository.findById(universe.getUniverseId());
        if (existingUniverse.isPresent()) {
            return universeRepository.save(universe);
        }else{
            throw new EntityNotFoundException("O Universo de ID: " + universe.getUniverseId()+" não foi encontrado.");
        }
    }

    @Transactional
    @Override
    public void deleteUniverse(Long universeId) {
        universeRepository.deleteById(universeId);
    }

    @Override
    public Optional<Universe> getUniverseById(Long universeId) {
        return universeRepository.findById(universeId);
    }

    @Override
    public List<Universe> getAllUniverses() {
        return universeRepository.findAll();
    }

    @Override
    public Optional<Universe> getUniverseByName(String name) {
        return universeRepository.findByName(name);
    }

    @Override
    public List<Universe> getAllUniverseByCreatorId(Long creatorId) {
        return universeRepository.findAllByCreator_UserId(creatorId);
    }

}
