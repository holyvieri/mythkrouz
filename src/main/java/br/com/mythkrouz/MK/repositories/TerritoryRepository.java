package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, Long> {

    @Query("SELECT t FROM Territory t WHERE t.name LIKE %:name%")
    public Optional<Territory> findByName(@Param("name") String territoryName);

    public List<Territory> findAllByUniverse_UniverseId(Long universeId);
}
