package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Universe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UniverseRepository extends JpaRepository<Universe, Long> {

    public List<Universe> findAllByCreator_UserId(Long creatorId);
    public Optional<Universe> findByName(String name);

}
