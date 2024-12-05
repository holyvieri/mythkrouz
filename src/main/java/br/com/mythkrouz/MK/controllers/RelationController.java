package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.RelationDTO;
import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<RelationDTO> createRelation(@RequestBody RelationDTO relation) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            RelationDTO createdRelation = relationService.createRelation(relation, user.getUsername());
            // o diamante a partir do java 7 ja detecta o T automaticamente, é a msma coisa de declarar la dentro
            return ResponseEntity.status(201).body(createdRelation);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelationDTO> updateRelation(@PathVariable Long id, @RequestBody RelationDTO relation) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RelationDTO updatedRelation = relationService.updateRelation(id, relation, user.getUsername());
        if (updatedRelation != null) {
            return ResponseEntity.ok(updatedRelation);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        relationService.deleteRelation(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relation> getRelationById(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Relation> relation = relationService.getRelationById(id, user.getUsername());
        return relation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<RelationDTO>> getAllRelations() {
        List<RelationDTO> relations = relationService.getAllRelations();
        return ResponseEntity.ok(relations);

    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<RelationDTO>> getRelationsByCharacterId(@PathVariable Long characterId) {
        List<RelationDTO> relations = relationService.getAllRelationsByCharacterId(characterId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping("/relations")
    public ResponseEntity<List<RelationDTO>> getAllRelationsByRelationType(@RequestParam RelationType relationType) {
        List<RelationDTO> relations = relationService.getAllRelationsByRelationType(relationType);
        return ResponseEntity.ok(relations);
    }



}
