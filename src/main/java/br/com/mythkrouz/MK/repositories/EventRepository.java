package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public Optional<Event> findEventByName(String eventName);
    List<Event> findByUniverse_UniverseId(Long universeId);
    public List<Event> findEventsByDate(LocalDate date);
    public List<Event> findAllByInvolvedCharacters_CharacterId(Long characterId);
    @Query("SELECT e FROM Event e JOIN e.territories t WHERE t.territoryId = :territoryId")
    public List<Event> findEventsByTerritoryId(Long territoryId);
}
