package br.com.mythkrouz.MK.dto;

import br.com.mythkrouz.MK.entities.enums.RelationType;

public record RelationDTO(
    Long relationId,
    Long character1Id,
    Long character2Id,
    RelationType relationType
) {}