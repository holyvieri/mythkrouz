package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.UserDTO;
import br.com.mythkrouz.MK.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive()
        );
    }
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setUserId(userDTO.userId());
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setActive(userDTO.isActive());

        //todo: senha vai ficar onde?

        return user;
    }
}
