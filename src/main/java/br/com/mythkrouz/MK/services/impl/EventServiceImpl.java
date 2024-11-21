package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.EventRepository;
import br.com.mythkrouz.MK.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public Event createEvent(Event event) throws EntityAlreadyExistsException {
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do Evento não pode ser nulo ou vazio.");
        }

        Optional<Event> existingEvent = eventRepository.findByName(event.getName());
        if (existingEvent.isPresent()) {
            throw new EntityAlreadyExistsException("evento");
        }

        return eventRepository.save(event);
    }

    @Transactional
    @Override
    public Event updateEvent(Event event) {
        Optional<Event> existingEvent = eventRepository.findById(event.getEventId());
        if (existingEvent.isPresent()) {
            return eventRepository.save(event);
        }else{
            throw new EntityNotFoundException("Evento com ID " + event.getEventId() + " não encontrado.");
        }
    }

    @Transactional
    @Override
    public void deleteEvent(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
        } else {
            throw new EntityNotFoundException("Evento não encontrado com o ID: " + eventId);
        }
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public Optional<Event> getEventByName(String eventName) {
        return eventRepository.findByName(eventName);
    }

    @Override
    public List<Event> getAllEventsByDate(LocalDate date) {
        return eventRepository.findAllByDate(date);
    }

    @Override
    public List<Event> getAllEventsByTerritoryId(Long territoryId) {
        return eventRepository.findAllByTerritory_TerritoryId(territoryId);
    }
}
