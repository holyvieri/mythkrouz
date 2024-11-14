package br.com.mythkrouz.MK.dto;

import br.com.mythkrouz.MK.entities.enums.CharacterClass;
import br.com.mythkrouz.MK.entities.enums.Gender;
import br.com.mythkrouz.MK.entities.enums.Race;

import java.util.List;

public record CharacterDTO(
        Long characterId,
        String name,
        Race race,
        CharacterClass characterClass,
        Gender gender,
        int age,
        String description,
        Long territoryId,
        List<Long> eventIds
) {}
