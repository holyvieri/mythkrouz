package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.CharacterDTO;
import br.com.mythkrouz.MK.dto.GetCharacterDTO;
import br.com.mythkrouz.MK.dto.GetMinCharacterDTO;
import br.com.mythkrouz.MK.entities.*;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.enums.CharacterClass;
import br.com.mythkrouz.MK.entities.enums.Gender;
import br.com.mythkrouz.MK.entities.enums.Race;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.CharacterMapper;
import br.com.mythkrouz.MK.repositories.*;
import br.com.mythkrouz.MK.services.CharacterService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final UniverseRepository universeRepository;
    private final TerritoryRepository territoryRepository;
    private final EventRepository eventRepository;

    //TODO seria bom dps mover essas validações para o bean validation (javax.validation) p n poluir o service

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, UserRepository userRepository, UniverseRepository universeRepository, TerritoryRepository territoryRepository, EventRepository eventRepository) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
        this.universeRepository = universeRepository;
        this.territoryRepository = territoryRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public CharacterDTO createCharacter(CharacterDTO character, String userEmail) throws EntityAlreadyExistsException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        //n é bom checar pelo id em caso de create n, pq o id não existe ainda, só vai ser gerado da criaçao
        Optional<Character> existingCharacter = characterRepository.findByName(character.name());
        if (existingCharacter.isPresent()) {
            throw new EntityAlreadyExistsException("Perosnagem");
        }

        // IDs dos universos criados pelo usuário
        List<Long> userUniverseIds = universeRepository.findAllByCreator_UserId(userId).stream()
                .map(Universe::getUniverseId)
                .toList();

        // Obter o território referenciado pelo personagem
        Territory territory = territoryRepository.findById(character.territoryId())
                .orElseThrow(() -> new IllegalArgumentException("Território não encontrado."));

//        System.out.println(territory);
        // Verificar se o universo do território está na lista de universos do usuário
        if (!userUniverseIds.contains(territory.getUniverse().getUniverseId())) {
            throw new IllegalArgumentException("O território do personagem não pertence ao usuário.");
        }

        // Cria a entidade Character com o território já associado
        Character newCharacter = CharacterMapper.toEntity(character);
        newCharacter.setTerritory(territory); // Associar o território antes de salvar
        newCharacter.setEvents(eventRepository.findAllById(character.eventIds()));

        Character savedCharacter = characterRepository.save(newCharacter); // Salvar o personagem

        return CharacterMapper.toDTO(savedCharacter);
    }

    @Override
    @Transactional
    public Character updateCharacter(Long id, CharacterDTO characterDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Character existingCharacter = characterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O Personagem de ID: " + id +
                        " não foi encontrado."));


        if (!userId.equals(existingCharacter.getTerritory().getUniverse().getCreator().getUserId())){
            throw new IllegalArgumentException("Usuário não autorizado a editar este personagem.");
        }

        if (characterDto.name() != null && !characterDto.name().trim().isEmpty()) {
            existingCharacter.setName(characterDto.name());
        }

        if (characterDto.race() != null) {
            try {
                Race race = Race.valueOf(characterDto.race().toString().toUpperCase());
                existingCharacter.setRace(race);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Raça inválida fornecida para o personagem: " + characterDto.race());
            }
        }

        if (characterDto.characterClass() != null) {
            try {
                CharacterClass characterClass = CharacterClass.valueOf(characterDto.characterClass().toString().toUpperCase());
                existingCharacter.setCharacterClass(characterClass);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Classe inválida fornecida para o personagem: " + characterDto.characterClass());
            }
        }

        if (characterDto.gender() != null) {
            try {
                Gender gender = Gender.valueOf(characterDto.gender().toString().toUpperCase());
                existingCharacter.setGender(gender);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Gênero inválido fornecido para o personagem: " + characterDto.gender());
            }
        }

        if (characterDto.age() > 0) {
            existingCharacter.setAge(characterDto.age());
        }

        if (characterDto.description() != null && !characterDto.description().trim().isEmpty()) {
            existingCharacter.setDescription(characterDto.description());
        }

        if (characterDto.territoryId() != null) {
            Territory territory = territoryRepository.findById(characterDto.territoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Território de ID: "
                            + characterDto.territoryId() + " não encontrado."));
            existingCharacter.setTerritory(territory);
        }

        if (characterDto.eventIds() != null && !characterDto.eventIds().isEmpty()) {
            List<Event> validEvents = characterDto.eventIds().stream()
                    .map(eventId -> eventRepository.findById(eventId)
                            .orElseThrow(() -> new EntityNotFoundException("Evento com ID: " + eventId + " não encontrado.")))
                    .collect(Collectors.toList());
            existingCharacter.setEvents(validEvents);
        }

        return characterRepository.save(existingCharacter);

    }

    @Transactional
    @Override
    public void deleteCharacter(Long characterId, String userEmail) {
        Character existingCharacter = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem de ID: "+ characterId+" não foi encontrado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingCharacter.getTerritory().getUniverse().getCreator().getUserId())){
            throw new IllegalArgumentException("Usuário não autorizado a deletar este personagem.");
        }

        characterRepository.deleteById(characterId);
    }

    @Override
    public Optional<Character> getCharacterById(Long id, String userEmail) {

        Optional<Character> existingCharacter = characterRepository.findById(id);
        if (existingCharacter.isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingCharacter.get().getTerritory().getUniverse().getCreator().getUserId())){
            throw new IllegalArgumentException("Usuário não autorizado a editar este personagem.");
        }

        return existingCharacter;
    }

    @Override
    public List<CharacterDTO> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        return characters.stream()
                .map(CharacterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Character> getCharacterByName(String characterName) {
        return characterRepository.findByName(characterName);
    }

    @Override
    public List<GetCharacterDTO> getAllCharactersByTerritoryId(Long territoryId) {

        List<Character> characters = characterRepository.findAllByTerritory_TerritoryId(territoryId);

        return characters.stream()
                .map(CharacterMapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetCharacterDTO> getAllCharactersByEventId(Long eventId) {
        List<Character> characters = characterRepository.findAllByEvents_EventId(eventId);
        return characters.stream()
                .map(CharacterMapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetMinCharacterDTO> getAllCharactersByRace(String race) {
        List<Character> characters = characterRepository.findAllByRace(Race.valueOf(race.toUpperCase()));
        return characters.stream()
                .map(CharacterMapper::toGetMinDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetMinCharacterDTO> getAllCharactersByGender(String gender) {
        List<Character> characters =characterRepository.findAllByGender(Gender.valueOf(gender.toUpperCase()));
        return characters.stream()
                .map(CharacterMapper::toGetMinDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetMinCharacterDTO> getAllCharactersByRaceAndGender(String race, String gender) {
        List<Character> characters = characterRepository.findAllByRaceAndGender(
                Race.valueOf(race.toUpperCase()),
                Gender.valueOf(gender.toUpperCase()));
        return characters.stream()
                .map(CharacterMapper::toGetMinDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetMinCharacterDTO> getAllCharactersByClass(String characterClass) {
        List<Character> characters = characterRepository.findAllByCharacterClass(
                CharacterClass.valueOf(characterClass.toUpperCase())
        );
        return characters.stream()
                .map(CharacterMapper::toGetMinDTO)
                .collect(Collectors.toList());
    }
}
