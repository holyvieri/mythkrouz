package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.EventDTO;
import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {
    public EventDTO createEvent(Event event, String userEmail) throws EntityAlreadyExistsException;
    public Event updateEvent(Long id, Event event, String userEmail);
    public void deleteEvent(Long eventId, String userEmail);
    public List<Event> getAllEvents();
    public Optional<Event> getEventById(Long eventId, String userEmail);
    public Optional<Event> getEventByName(String eventName);
    public List<Event> getAllEventsByDate(LocalDate date);
    public List<Event> getAllEventsByTerritoryId(Long territoryId);

}
