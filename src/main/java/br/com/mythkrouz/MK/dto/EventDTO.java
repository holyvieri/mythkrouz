package br.com.mythkrouz.MK.dto;

import java.time.LocalDate;
import java.util.List;

public record EventDTO(
        Long eventId,
        String name,
        String description,
        LocalDate date,
        List<Long> territoryIds
) {}
