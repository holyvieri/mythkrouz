package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.UpdateUniverseDTO;
import br.com.mythkrouz.MK.entities.Universe;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.UniverseService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/create")
    public ResponseEntity<Universe> createUniverse(@RequestBody Universe universe) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Universe createdUniverse = universeService.createUniverse(universe, user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUniverse);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{universeId}")
    public ResponseEntity<Universe> updateUniverse(@PathVariable Long universeId,
                                                   @RequestBody UpdateUniverseDTO universe){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Universe updatedUniverse = universeService.updateUniverse(universe, universeId, user.getUsername());

        //boa pratica
        if (updatedUniverse != null) {
            return ResponseEntity.ok(updatedUniverse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{universeId}")
    public ResponseEntity<Void> deleteUniverse(@PathVariable Long universeId) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        universeService.deleteUniverse(universeId, user.getUsername());

        return ResponseEntity.noContent().build(); //no content vai ser justamente oq a gnt quer afirmar
    }

    @GetMapping("/{universeId}")
    public ResponseEntity<Universe> getUniverseById(@PathVariable Long universeId) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Universe> universe = universeService.getUniverseById(universeId, user.getUsername());

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
        List<Universe> universes = universeService.getAllUniverseByCreatorId(creatorId);
        return ResponseEntity.ok(universes);

    }


}
