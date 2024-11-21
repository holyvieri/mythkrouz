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
    public Optional<Event> findByName(String eventName);
    public List<Event> findAllByDate(LocalDate date);
    public List<Event> findAllByInvolvedCharacters_CharacterId(Long characterId);
    public List<Event> findAllByTerritoryId(Long territoryId);
}
