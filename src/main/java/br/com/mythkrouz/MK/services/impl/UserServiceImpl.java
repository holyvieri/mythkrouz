package br.com.mythkrouz.MK.services.impl;


import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.UserRepository;
import br.com.mythkrouz.MK.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User createUser(User user) throws EntityAlreadyExistsException {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }

        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("Usuário");
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            return userRepository.save(user);
        }else{
            throw new EntityNotFoundException("O Usuário de email: "+user.getEmail()+" não foi encontrado.");
        }
    }

    @Transactional
    @Override
    public void deleteUser_UserId(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndAndPassword(email, password);
    }
}
