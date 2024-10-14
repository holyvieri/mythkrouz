package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findByUniverseId(Long universeId);
    public List<Event> findByDate(LocalDate date);
    public List<Event> findByInvolvedCharacters(Long characterId);
    @Query("SELECT e FROM Event e JOIN e.territories t WHERE t.id = :territoryId")
    public List<Event> findByTerritoryId(Long territoryId);
}
