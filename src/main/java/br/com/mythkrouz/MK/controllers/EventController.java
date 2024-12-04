package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.EventDTO;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.CharacterService;
import br.com.mythkrouz.MK.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final CharacterService characterService;

    @Autowired
    public EventController(EventService eventService, CharacterService characterService) {
        this.eventService = eventService;
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody Event event) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            EventDTO createdEvent = eventService.createEvent(event, user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Event updatedEvent = eventService.updateEvent(id, event, user.getUsername());
        try {
            return ResponseEntity.ok(updatedEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        eventService.deleteEvent(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Event> event = eventService.getEventById(id, user.getUsername());
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/name/{eventName}")
    public ResponseEntity<Event> getEventByName(@PathVariable String eventName) {
        Optional<Event> event = eventService.getEventByName(eventName);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/date/{date}")
    public ResponseEntity<List<Event>> getEventsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Event> events = eventService.getAllEventsByDate(localDate);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/territory/{territoryId}")
    public ResponseEntity<List<Event>> getEventsByTerritoryId(@PathVariable Long territoryId) {
        List<Event> events = eventService.getAllEventsByTerritoryId(territoryId);
        return ResponseEntity.ok(events);
    }



}
