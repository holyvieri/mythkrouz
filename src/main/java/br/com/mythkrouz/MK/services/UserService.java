package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User createUser(User user) throws EntityAlreadyExistsException;
    public User updateUser(User user);
    public void deleteUser_UserId(Long userId);
    public Optional<User> getUserById(Long userId);
    public List<User> getAllUsers();
    public Optional<User> getUserByEmail(String email);
    public Optional<User> getUserByUsername(String username);
    public Optional<User> getUserByEmailAndPassword(String email, String password);

}
