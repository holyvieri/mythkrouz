package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.TerritoryDTO;
import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.TerritoryMapper;
import br.com.mythkrouz.MK.repositories.TerritoryRepository;
import br.com.mythkrouz.MK.repositories.UniverseRepository;
import br.com.mythkrouz.MK.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final UniverseRepository universeRepository;

    @Autowired
    public TerritoryServiceImpl(TerritoryRepository territoryRepository, UserRepository userRepository, UniverseRepository universeRepository) {
        this.territoryRepository = territoryRepository;
        this.userRepository = userRepository;
        this.universeRepository = universeRepository;
    }

    @Transactional
    @Override
    public TerritoryDTO createTerritory(Territory territory, String userEmail) throws EntityAlreadyExistsException{
        if (territory.getName() == null || territory.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do território não pode ser nulo ou vazio.");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Optional<Territory> existingTerritory = territoryRepository.findByName(territory.getName());
        if (existingTerritory.isPresent()){
            throw new EntityAlreadyExistsException("Território");
        }

        Long universeId = territory.getUniverse().getUniverseId();

        List<Universe> userUniverses = universeRepository.findAllByCreator_UserId(userId);
//        System.out.println(userUniverses.get(0).getName());
        boolean isUserOwnerOfUniverse = userUniverses.stream().anyMatch(universe ->
                universe.getUniverseId()
                        .equals(universeId));
        System.out.println(isUserOwnerOfUniverse);

        if (!isUserOwnerOfUniverse){
            throw new IllegalArgumentException("Usuário não autorizado a criar um território neste universo.");
        }

        Territory savedTerritory = territoryRepository.save(territory);
        return TerritoryMapper.toDTO(savedTerritory);
    }

    @Transactional
    @Override
    public Territory updateTerritory(Territory territory, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Optional<Territory> existingTerritory = territoryRepository.findById(territory.getTerritoryId());
        if (existingTerritory.isPresent()) {
            return territoryRepository.save(territory);
        }else{
            throw new EntityNotFoundException("Território com ID" + territory.getTerritoryId() + " não encontrado.");
        }
    }

    @Override
    public void deleteTerritory(Long territoryId, String userEmail) {
        territoryRepository.deleteById(territoryId);
    }

    @Override
    public Optional<Territory> getTerritoryById(Long territoryId, String userEmail) {
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
        return territoryRepository.findAllByUniverse_UniverseId(universeId);
    }
}
