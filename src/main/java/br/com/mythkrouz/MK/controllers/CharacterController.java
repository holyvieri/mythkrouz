package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.CharacterDTO;
import br.com.mythkrouz.MK.dto.GetCharacterDTO;
import br.com.mythkrouz.MK.dto.GetMinCharacterDTO;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.CharacterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    private final CharacterService characterService;
    @Autowired
    public CharacterController(final CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<CharacterDTO> createCharacter(@RequestBody CharacterDTO characterDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            CharacterDTO createdCharacter = characterService.createCharacter(characterDto, user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody CharacterDTO characterDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Character updatedCharacter = characterService.updateCharacter(id, characterDto, user.getUsername());
        if (updatedCharacter != null) {
            return ResponseEntity.ok(updatedCharacter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            characterService.deleteCharacter(id, user.getUsername());
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Character> character = characterService.getCharacterById(id, user.getUsername());
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{characterName}")
    public ResponseEntity<Character> getCharacterByName(@PathVariable String characterName) {
        Optional<Character> character = characterService.getCharacterByName(characterName);
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getAllCharacters() {
        List<CharacterDTO> characters = characterService.getAllCharacters();
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/territory/{territoryId}")
    public ResponseEntity<List<GetCharacterDTO>> getCharactersByTerritoryId(@PathVariable Long territoryId) {
        List<GetCharacterDTO> characters = characterService.getAllCharactersByTerritoryId(territoryId);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<GetCharacterDTO>> getCharactersByEventId(@PathVariable Long eventId) {
        List<GetCharacterDTO> characters = characterService.getAllCharactersByEventId(eventId);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/race/{race}")
    public ResponseEntity<List<GetMinCharacterDTO>> getCharactersByRace(@PathVariable String race) {
        List<GetMinCharacterDTO> characters = characterService.getAllCharactersByRace(race);
        if (characters.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<GetMinCharacterDTO>> getCharactersByGender(@PathVariable String gender) {
        List<GetMinCharacterDTO> characters = characterService.getAllCharactersByGender(gender);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }


    @GetMapping("/race-gender")
    public ResponseEntity<List<GetMinCharacterDTO>> getCharactersByRaceAndGender(
            @RequestParam String race,
            @RequestParam String gender) {
        List<GetMinCharacterDTO> characters = characterService.getAllCharactersByRaceAndGender(race, gender);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/class/{characterClass}")
    public ResponseEntity<List<GetMinCharacterDTO>> getCharactersByClass(@PathVariable String characterClass) {
        List<GetMinCharacterDTO> characters = characterService.getAllCharactersByClass(characterClass);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }


}
