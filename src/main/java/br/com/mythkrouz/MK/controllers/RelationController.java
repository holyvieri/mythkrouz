package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/relations")
public class RelationController {

    private final RelationService relationService;
    @Autowired
    public RelationController(final RelationService relationService) {
        this.relationService = relationService;
    }

    @PostMapping
    public ResponseEntity<Relation> createRelation(@RequestBody Relation relation) {
        try {
            Relation createdRelation = relationService.createRelation(relation);
            // o diamante a partir do java 7 ja detecta o T automaticamente, é a msma coisa de declarar la dentro
            return new ResponseEntity<>(createdRelation, HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relation> updateRelation(@PathVariable Long id, @RequestBody Relation relation) {
        Optional<Relation> existingRelation = relationService.getRelationById(id);
        if (existingRelation.isPresent()) {
            relation.setRelationId(id);
            Relation updatedRelation = relationService.updateRelation(relation);
            return new ResponseEntity<>(updatedRelation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long id) {
        relationService.deleteRelation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relation> getRelationById(@PathVariable Long id) {
        Optional<Relation> relation = relationService.getRelationById(id);
        return relation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<Relation>> getAllRelations() {
        List<Relation> relations = relationService.getAllRelations();
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<Relation>> getRelationsByCharacterId(@PathVariable Long characterId) {
        List<Relation> relations = relationService.getRelationsByCharacterId(characterId);
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }




}
