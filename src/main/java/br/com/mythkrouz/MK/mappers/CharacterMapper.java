package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.CharacterDTO;
import br.com.mythkrouz.MK.entities.Character;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class CharacterMapper {

    public static CharacterDTO toDTO(Character character) {
        if (character == null) {
            return null;
        }

        return new CharacterDTO(
                character.getCharacterId(),
                character.getName(),
                character.getRace(),
                character.getCharacterClass(),
                character.getGender(),
                character.getAge(),
                character.getDescription(),
                character.getTerritory().getTerritoryId(),
                character.getEvents().stream()// cria um stream da lista de eventos
                        .map(event -> event.getEventId()) // mapeia cada evento para o seu ID
                        .collect(Collectors.toList()) // coleta os resultados em uma lista
        );
    }

    public static Character toEntity(CharacterDTO characterDTO) {
        if (characterDTO == null) {
            return null;
        }

        Character character = new Character();
        character.setCharacterId(characterDTO.characterId());
        character.setName(characterDTO.name());
        character.setRace(characterDTO.race());
        character.setCharacterClass(characterDTO.characterClass());
        character.setGender(characterDTO.gender());
        character.setAge(characterDTO.age());
        character.setDescription(characterDTO.description());

        //Todo: terriroyId e eventId no service

        return character;
    }
}
