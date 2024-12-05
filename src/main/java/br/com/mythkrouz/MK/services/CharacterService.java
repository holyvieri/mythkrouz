package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.CharacterDTO;
import br.com.mythkrouz.MK.dto.GetCharacterDTO;
import br.com.mythkrouz.MK.dto.GetMinCharacterDTO;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    public CharacterDTO createCharacter(CharacterDTO character, String userEmail) throws EntityAlreadyExistsException;
    public Character updateCharacter(Long id, CharacterDTO character, String userEmail);
    public void deleteCharacter(Long id, String userEmail);
    public Optional<Character> getCharacterById(Long id, String userEmail);
    public List<CharacterDTO> getAllCharacters();
    public Optional<Character> getCharacterByName(String characterName);
    public List<GetCharacterDTO> getAllCharactersByTerritoryId(Long territoryId);
    public List<GetCharacterDTO> getAllCharactersByEventId(Long eventId);
    public List<GetMinCharacterDTO> getAllCharactersByRace(String race);
    public List<GetMinCharacterDTO> getAllCharactersByGender(String gender);
    public List<GetMinCharacterDTO> getAllCharactersByRaceAndGender(String race, String gender);
    public List<GetMinCharacterDTO> getAllCharactersByClass(String characterClass);


}
