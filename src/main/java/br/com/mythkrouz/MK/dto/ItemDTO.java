package br.com.mythkrouz.MK.dto;

import br.com.mythkrouz.MK.entities.enums.Rarity;
import br.com.mythkrouz.MK.entities.enums.Type;

import java.util.List;

public record ItemDTO(
        Long itemId,
        String name,
        String description,
        Type type,
        Rarity rarity,
        Long territoryId,
        List<Long> ownerIds
) {}
