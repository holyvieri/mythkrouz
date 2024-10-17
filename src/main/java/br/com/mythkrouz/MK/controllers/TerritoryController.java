package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/territories")
public class TerritoryController {

    private final TerritoryService territoryService;

    @Autowired
    public TerritoryController(TerritoryService territoryService) {
        this.territoryService = territoryService;
    }

    @PostMapping
    public ResponseEntity<Territory> createTerritory(@RequestBody Territory territory) {
        try {
            Territory createdTerritory = territoryService.createTerritory(territory);
            return ResponseEntity.status(201).body(createdTerritory);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Territory> updateTerritory(@PathVariable Long id, @RequestBody Territory territory) {
        Optional<Territory> existingTerritory = territoryService.getTerritoryById(id);
        if (existingTerritory.isPresent()) {
            territory.setTerritoryId(id);
            Territory updatedTerritory = territoryService.updateTerritory(territory);
            return ResponseEntity.ok(updatedTerritory);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerritory(@PathVariable Long id) {
        territoryService.deleteTerritory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Territory> getTerritoryById(@PathVariable Long id) {
        Optional<Territory> territory = territoryService.getTerritoryById(id);
        return territory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Territory>> getAllTerritories() {
        List<Territory> territories = territoryService.getAllTerritory();
        return ResponseEntity.ok(territories);
    }

    @GetMapping("/name/{territoryName}")
    public ResponseEntity<Territory> getTerritoryByName(@PathVariable String territoryName) {
        Optional<Territory> territory = territoryService.getTerritoryByName(territoryName);
        return territory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/universe/{universeId}")
    public ResponseEntity<List<Territory>> getTerritoriesByUniverse(@PathVariable Long universeId) {
        List<Territory> territories = territoryService.getTerritoriesByUniverse(universeId);
        return ResponseEntity.ok(territories);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<Territory>> getTerritoriesByRelatedEvents(@PathVariable Long eventId) {
        List<Territory> territories = territoryService.getTerritoriesByRelatedEvents(eventId);
        return ResponseEntity.ok(territories);
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<List<Territory>> getTerritoriesByRelatedItems(@PathVariable Long itemId) {
        List<Territory> territories = territoryService.getTerritoriesByRelatedItems(itemId);
        return ResponseEntity.ok(territories);
    }

    //TODO: os metodos no postman tao aparecendo tudo tipo concha dentro da concha de 5 a seco

}
