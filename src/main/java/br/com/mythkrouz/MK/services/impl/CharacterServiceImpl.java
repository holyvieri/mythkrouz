package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.CharacterRepository;
import br.com.mythkrouz.MK.services.CharacterService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    //TODO seria bom dps mover essas validações para o bean validation (javax.validation) p n poluir o service

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    @Transactional
    public Character createCharacter(Character character) throws EntityAlreadyExistsException {

        // o trim vai retirar espaço em branco do inicio e do fim da string
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do personagem não pode ser nulo ou estar vazio.");
        }
        //n é bom checar pelo id em caso de create n, pq o id não existe ainda, só vai ser gerado da criaçao
        Optional<Character> existingCharacter = characterRepository.findByName(character.getName());

        if (existingCharacter.isPresent()) {
            throw new EntityAlreadyExistsException("Perosnagem");
        }

        return characterRepository.save(character);
    }

    @Override
    @Transactional
    public Character updateCharacter(Character character) {
        Optional<Character> existingCharacter = characterRepository.findById(character.getCharacterId());
        if (existingCharacter.isPresent()) {
            return characterRepository.save(character);
        }else{
            throw new EntityNotFoundException("Personagem com ID " + character.getCharacterId() + " não encontrado.");
        }
    }

    @Transactional
    @Override
    public void deleteCharacter(Long characterId) {
        characterRepository.deleteById(characterId);
    }

    @Override
    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    public Optional<Character> getCharacterByName(String characterName) {
        return characterRepository.findByName(characterName);
    }

    @Override
    public List<Character> getCharactersByUniverseId(Long universeId) {
        return characterRepository.findByUniverse_UniverseId(universeId);
    }

    @Override
    public List<Character> getCharactersByRace(String race) {
        return characterRepository.findAllByRace(race);
    }

    @Override
    public List<Character> getCharactersByGender(String gender) {
        return characterRepository.findAllByGender(gender);
    }

    @Override
    public List<Character> getCharactersByRaceAndGender(String race, String gender) {
        return characterRepository.findAllByRaceAndGender(race, gender);
    }

    @Override
    public List<Character> getCharactersByClass(String characterClass) {
        return characterRepository.findAllByCharacterClass(characterClass);
    }
}
