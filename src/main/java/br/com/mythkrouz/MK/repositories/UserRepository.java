package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByEmailAndAndPassword(String email, String password);

}
