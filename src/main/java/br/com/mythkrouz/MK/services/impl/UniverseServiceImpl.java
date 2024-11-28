package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.UpdateUniverseDTO;
import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.UniverseRepository;
import br.com.mythkrouz.MK.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public UniverseServiceImpl(UniverseRepository universeRepository, UserRepository userRepository) {
        this.universeRepository = universeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Universe createUniverse(Universe universe, String userEmail) throws EntityAlreadyExistsException {
        if (universe.getName() == null || universe.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do Universo não pode ser nulo ou vazio.");
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Optional<Universe> existingUniverse = universeRepository.findByName(universe.getName());
        if (existingUniverse.isPresent()) {
            throw new EntityAlreadyExistsException("Universo");
        }

        universe.setCreator(userRepository.findById(userId).get());

        return universeRepository.save(universe);
    }


    @Transactional
    @Override
    public Universe updateUniverse(UpdateUniverseDTO universeDto, Long universeId, String userEmail) {

        Universe existingUniverse = universeRepository.findById(universeId)
                .orElseThrow(() -> new EntityNotFoundException("O Universo de ID: " + universeId +
                                " não foi encontrado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingUniverse.getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a atualizar este universo.");
        }
        if (universeDto.name() != null) {
            existingUniverse.setName(universeDto.name());
        }
        if (universeDto.description() != null) {
            existingUniverse.setDescription(universeDto.description());
        }
        return universeRepository.save(existingUniverse);
    }

    @Transactional
    @Override
    public void deleteUniverse(Long universeId, String userEmail) {
        Universe existingUniverse = universeRepository.findById(universeId)
                .orElseThrow(() -> new EntityNotFoundException("O Universo de ID: " + universeId +
                        " não foi encontrado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingUniverse.getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este universo.");
        }
        universeRepository.deleteById(universeId);
    }

    @Override
    public Optional<Universe> getUniverseById(Long universeId, String userEmail) {

        Optional<Universe> existingUniverse = universeRepository.findById(universeId);

        if (existingUniverse.isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingUniverse.get().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a visualizar este universo.");
        }

        return existingUniverse;
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
