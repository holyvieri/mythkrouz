package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.name LIKE %:name%")
    public Optional<Event> findByName(@Param("name") String eventName);

    public List<Event> findAllByDate(LocalDate date);
    public List<Event> findAllByTerritory_TerritoryId(Long territoryId);
}
