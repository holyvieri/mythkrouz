package br.com.mythkrouz.MK.dto;

import java.time.LocalDate;

public record UniverseDTO (
        Long universeId,
        String name,
        String description,
        LocalDate creationDate,
        Long creatorId
){}
