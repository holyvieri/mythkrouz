package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.TerritoryDTO;
import br.com.mythkrouz.MK.dto.UpdateTerritoryDTO;
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
    public Territory updateTerritory(Long id, UpdateTerritoryDTO territory, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Territory existingTerritory = territoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O Território de ID: " + id +
                        " não foi encontrado."));

        if (!userId.equals(existingTerritory.getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a atualizar este território.");
        }

        if (territory.name() != null){
            existingTerritory.setName(territory.name());
        }
        if (territory.description() != null){
            existingTerritory.setDescription(territory.description());
        }

        return territoryRepository.save(existingTerritory);
    }

    @Override
    public void deleteTerritory(Long territoryId, String userEmail) {
        Territory existingTerritory = territoryRepository.findById(territoryId)
                .orElseThrow(() -> new EntityNotFoundException("Território de ID: " + territoryId + " não foi encontrado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingTerritory.getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este território.");
        }

        territoryRepository.delete(existingTerritory);
    }

    @Override
    public Optional<Territory> getTerritoryById(Long territoryId, String userEmail) {

        Optional<Territory> existingTerritory =  territoryRepository.findById(territoryId);

        if (existingTerritory.isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingTerritory.get().getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este território.");
        }

        return existingTerritory;
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
