package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.UserDTO;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.UserMapper;
import br.com.mythkrouz.MK.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try{
            userService.deleteUser_UserId(id);
            return ResponseEntity.ok().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        // Se o Optional conter valor, aplica o mapeamento para DTO e retorna com status OK.
        return user
                .map(UserMapper::toDTO) // Converte a entidade User para UserDTO
                .map(ResponseEntity::ok) // Retorna ResponseEntity com status OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Caso contrário, retorna NOT FOUND.

    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        // Converte a lista de User em uma lista de UserDTO
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO) // Aplica o mapeamento para cada User
                .toList(); // Coleta os resultados em uma lista

        return ResponseEntity.ok(userDTOs); // Retorna a lista de UserDTO com status 200 OK
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user
                .map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user
                .map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
