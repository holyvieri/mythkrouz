package br.com.mythkrouz.MK.dto;

public record UserDTO(
        Long userId,
        String username,
        String email,
        boolean isActive
) {}
