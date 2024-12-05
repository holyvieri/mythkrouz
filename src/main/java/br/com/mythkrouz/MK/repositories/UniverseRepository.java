package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Universe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UniverseRepository extends JpaRepository<Universe, Long> {

    public List<Universe> findAllByCreator_UserId(Long creatorId);

    @Query("SELECT u FROM Universe u WHERE u.name LIKE %:name%")
    public Optional<Universe> findByName(@Param("name") String name);

}
