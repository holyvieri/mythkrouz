package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.EventDTO;
import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.EventMapper;
import br.com.mythkrouz.MK.repositories.EventRepository;
import br.com.mythkrouz.MK.repositories.UniverseRepository;
import br.com.mythkrouz.MK.repositories.UserRepository;
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
    private final UniverseRepository universeRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UniverseRepository universeRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.universeRepository = universeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public EventDTO createEvent(Event event, String userEmail) throws EntityAlreadyExistsException {
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do Evento não pode ser nulo ou vazio.");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();


        Optional<Event> existingEvent = eventRepository.findByName(event.getName());
        if (existingEvent.isPresent()) {
            throw new EntityAlreadyExistsException("Evento");
        }

        //ids dos universes do user
        List<Long> userUniverseIds = universeRepository.findAllByCreator_UserId(userId).stream()
                .map(Universe::getUniverseId)
                .toList();

        //ids dos universos referenciados pelos territorios que estao a ser setados
        List<Long> territoriesUniversesId = event.getTerritory().stream()
                .map(territory -> territory.getUniverse().getUniverseId())
                .toList();

        //checar agr se todos os ids universe dos territorios selecionados de fato estao dentro da lista de ids do user
        boolean isValid = territoriesUniversesId.stream()
                        .allMatch(userUniverseIds::contains);

        if (!isValid) {
            throw new IllegalArgumentException("Um ou mais territórios não são pertencentes ao usuário.");
        }

        Event savedEvent = eventRepository.save(event);
        return EventMapper.toDTO(savedEvent);
    }

    @Transactional
    @Override
    public Event updateEvent(Long id, Event event, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O Evento de ID: " + id +
                        " não foi encontrado."));

        boolean isAuthorized = existingEvent.getTerritory().stream()
                        .map(territory -> territory.getUniverse().getCreator().getUserId())
                .allMatch(creatorUserId -> creatorUserId.equals(userId));

        if (!isAuthorized) {
            throw new IllegalArgumentException("Usuário não autorizado a editar este território");
        }

        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        existingEvent.setTerritory(event.getTerritory());

        return eventRepository.save(existingEvent);
    }

    @Transactional
    @Override
    public void deleteEvent(Long eventId, String userEmail) {

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento de ID: "+ eventId+" não foi encontrado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        boolean isAuthorized = existingEvent.getTerritory().stream()
                .map(territory -> territory.getUniverse().getCreator().getUserId())
                .allMatch(creatorUserId -> creatorUserId.equals(userId));

        if (!isAuthorized) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este evento.");
        }

        eventRepository.delete(existingEvent);

    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long eventId, String userEmail) {

        Optional<Event> existingEvent = eventRepository.findById(eventId);

        if (existingEvent.isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        boolean isAuthorized = existingEvent.get().getTerritory().stream()
                .map(territory -> territory.getUniverse().getCreator().getUserId())
                .allMatch(creatorUserId -> creatorUserId.equals(userId));

        if (!isAuthorized) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este evento.");
        }

        return existingEvent;
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
