package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.UniverseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/universes")
public class UniverseController {

    private final UniverseService universeService;

    @Autowired
    public UniverseController(UniverseService universeService) {
        this.universeService = universeService;
    }

    @PostMapping
    public ResponseEntity<Universe> createUniverse(@RequestBody Universe universe) {
        try {
            Universe createdUniverse = universeService.createUniverse(universe);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUniverse);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{universeId}")
    public ResponseEntity<Universe> updateUniverse(@PathVariable Long universeId, @RequestBody Universe universe) {
        universe.setUniverseId(universeId); //boa pratica
        Universe updatedUniverse = universeService.updateUniverse(universe);
        if (updatedUniverse != null) {
            return ResponseEntity.ok(updatedUniverse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{universeId}")
    public ResponseEntity<Void> deleteUniverse(@PathVariable Long universeId) {
        universeService.deleteUniverse(universeId);
        return ResponseEntity.noContent().build(); //no content vai ser justamente oq a gnt quer afirmar
    }

    @GetMapping("/{universeId}")
    public ResponseEntity<Universe> getUniverseById(@PathVariable Long universeId) {
        Optional<Universe> universe = universeService.getUniverseById(universeId);
        return universe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Universe>> getAllUniverses() {
        List<Universe> universes = universeService.getAllUniverses();
        return ResponseEntity.ok(universes);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Universe> getUniverseByName(@PathVariable String name) {
        Optional<Universe> universe = universeService.getUniverseByName(name);
        return universe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<Universe>> getUniversesByCreatorId(@PathVariable Long creatorId) {
        List<Universe> universes = universeService.getUniverseByCreatorId(creatorId);
        return ResponseEntity.ok(universes);

    }

    @GetMapping("/territory/{territoryId}")
    public ResponseEntity<Universe> getUniversesByTerritoryId(@PathVariable Long territoryId) {
        Optional<Universe> universe = universeService.getUniverseByTerritoryId(territoryId);
        return universe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<Universe> getUniversesByCharacterId(@PathVariable Long characterId) {
        Optional<Universe> universe = universeService.getUniverseByCharacterId(characterId);
        return universe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
