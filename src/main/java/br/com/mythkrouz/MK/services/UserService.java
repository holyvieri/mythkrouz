package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User createUser(User user) throws Exception;
    public User updateUser(User user);
    public void deleteUser(User user);
    public void deleteUser(Long id);
    public Optional<User> getUserById(Long id);
    public List<User> getAllUsers();
    public Optional<User> getUserByEmail(String email);
    public Optional<User> getUserByUsername(String username);
    public Optional<User> getUserByEmailAndPassword(String email, String password);

}
