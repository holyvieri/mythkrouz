package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {
    public Event createEvent(Event event) throws EntityAlreadyExistsException;
    public Event updateEvent(Event event);
    public void deleteEvent(Long eventId);
    public List<Event> getAllEvents();
    public Optional<Event> getEventById(Long eventId);
    public Optional<Event> getEventByName(String eventName);
    public List<Event> getEventsByUniverseId(Long universeId);
    public List<Event> getEventsByDate(LocalDate date);
    public List<Event> getEventsByInvolvedCharacter(Long involvedCharacterId);
    public List<Event> getEventsByTerritoryId(Long territoryId);

}
