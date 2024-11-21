package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.TerritoryRepository;
import br.com.mythkrouz.MK.services.TerritoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerritoryServiceImpl implements TerritoryService {

    private final TerritoryRepository territoryRepository;

    @Autowired
    public TerritoryServiceImpl(TerritoryRepository territoryRepository) {
        this.territoryRepository = territoryRepository;
    }

    @Transactional
    @Override
    public Territory createTerritory(Territory territory) throws EntityAlreadyExistsException {
        if (territory.getName() == null || territory.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do território não pode ser nulo ou vazio.");
        }

        Optional<Territory> existingTerritory = territoryRepository.findByName(territory.getName());
        if (existingTerritory.isPresent()) {
            throw new EntityAlreadyExistsException("Território");
        }

        return territoryRepository.save(territory);
    }

    @Transactional
    @Override
    public Territory updateTerritory(Territory territory) {
        Optional<Territory> existingTerritory = territoryRepository.findById(territory.getTerritoryId());
        if (existingTerritory.isPresent()) {
            return territoryRepository.save(territory);
        }else{
            throw new EntityNotFoundException("Território com ID" + territory.getTerritoryId() + " não encontrado.");
        }
    }

    @Override
    public void deleteTerritory(Long territoryId) {
        territoryRepository.deleteById(territoryId);
    }

    @Override
    public Optional<Territory> getTerritoryById(Long territoryId) {
        return territoryRepository.findById(territoryId);
    }

    @Override
    public List<Territory> getAllTerritory() {
        return territoryRepository.findAll();
    }

    @Override
    public Optional<Territory> getTerritoryByName(String territoryName) {
        return territoryRepository.findByName(territoryName);
    }

    @Override
    public List<Territory> getAllTerritoriesByUniverse(Long universeId) {
        return territoryRepository.findAllByUniverseId(universeId);
    }
}
