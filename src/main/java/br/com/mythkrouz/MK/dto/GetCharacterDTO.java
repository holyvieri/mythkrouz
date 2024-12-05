package br.com.mythkrouz.MK.dto;

import br.com.mythkrouz.MK.entities.enums.CharacterClass;
import br.com.mythkrouz.MK.entities.enums.Gender;
import br.com.mythkrouz.MK.entities.enums.Race;

public record GetCharacterDTO(
        Long characterId,
        String name,
        Race race,
        CharacterClass characterClass,
        Gender gender,
        int age

) {}
