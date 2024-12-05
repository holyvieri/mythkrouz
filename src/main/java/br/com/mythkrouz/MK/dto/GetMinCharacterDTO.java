package br.com.mythkrouz.MK.dto;

public record GetMinCharacterDTO(
        Long characterId,
        String name,
        int age,
        String description
) {
}
