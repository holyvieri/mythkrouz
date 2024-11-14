package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.EventDTO;
import br.com.mythkrouz.MK.entities.Event;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    public static EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        return new EventDTO(
                event.getEventId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getTerritory().stream()
                        .map(territory -> territory.getTerritoryId())
                        .collect(Collectors.toList())
        );
    }

    public static Event toEntity(EventDTO eventDTO) {
        if (eventDTO == null) {
            return null;
        }

        Event event = new Event();
        event.setEventId(eventDTO.eventId());
        event.setName(eventDTO.name());
        event.setDescription(eventDTO.description());
        event.setDate(eventDTO.date());

        //TODO: territoryid no service

        return event;
    }
}
