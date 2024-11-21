package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.CharacterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<br.com.mythkrouz.MK.entities.Character> createCharacter
            (@RequestBody br.com.mythkrouz.MK.entities.Character character) {
        try {
            Character createdCharacter = characterService.createCharacter(character);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCharacter);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable Long id, @RequestBody Character character) {
        character.setCharacterId(id);
        Character updatedCharacter = characterService.updateCharacter(character);
        if (updatedCharacter != null) {
            return ResponseEntity.ok(updatedCharacter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteCharacter(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        Optional<Character> character = characterService.getCharacterById(id);
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{characterName}")
    public ResponseEntity<Character> getCharacterByName(@PathVariable String characterName) {
        Optional<Character> character = characterService.getCharacterByName(characterName);
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterService.getAllCharacters();
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/territory/{territoryId}")
    public ResponseEntity<List<Character>> getCharactersByTerritoryId(@PathVariable Long territoryId) {
        List<Character> characters = characterService.getAllCharactersByTerritoryId(territoryId);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/race/{race}")
    public ResponseEntity<List<Character>> getCharactersByRace(@PathVariable String race) {
        List<Character> characters = characterService.getAllCharactersByRace(race);
        if (characters.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Character>> getCharactersByGender(@PathVariable String gender) {
        List<Character> characters = characterService.getAllCharactersByGender(gender);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }


    @GetMapping("/race-gender")
    public ResponseEntity<List<Character>> getCharactersByRaceAndGender(
            @RequestParam String race,
            @RequestParam String gender) {
        List<Character> characters = characterService.getAllCharactersByRaceAndGender(race, gender);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/class/{characterClass}")
    public ResponseEntity<List<Character>> getCharactersByClass(@PathVariable String characterClass) {
        List<Character> characters = characterService.getAllCharactersByClass(characterClass);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Character>> getCharactersByEventId(@PathVariable Long eventId) {
        List<Character> characters = characterService.getAllCharactersByEventId(eventId);
        if (characters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(characters);
    }

}
