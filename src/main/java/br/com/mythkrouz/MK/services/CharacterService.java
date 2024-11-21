package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    public Character createCharacter(Character character) throws EntityAlreadyExistsException;
    public Character updateCharacter(Character character);
    public void deleteCharacter(Long id);
    public Optional<Character> getCharacterById(Long id);
    public List<Character> getAllCharacters();
    public Optional<Character> getCharacterByName(String characterName);
    public List<Character> getCharactersByTerritoryId(Long territoryId);
    public List<Character> getCharactersByEventId(Long eventId);
    public List<Character> getCharactersByRace(String race);
    public List<Character> getCharactersByGender(String gender);
    public List<Character> getCharactersByRaceAndGender(String race, String gender);
    public List<Character> getCharactersByClass(String characterClass);

    //TODO: FALTOU CHARACTER BY TERRITORY

}
