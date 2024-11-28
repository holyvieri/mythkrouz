package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.TerritoryDTO;
import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<TerritoryDTO> createTerritory(@RequestBody Territory territory) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            TerritoryDTO createdTerritory = territoryService.createTerritory(territory, user.getUsername());
            return ResponseEntity.status(201).body(createdTerritory);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Territory> updateTerritory(@PathVariable Long id, @RequestBody Territory territory) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Territory> existingTerritory = territoryService.getTerritoryById(id, user.getUsername());
        if (existingTerritory.isPresent()) {
            territory.setTerritoryId(id);
            Territory updatedTerritory = territoryService.updateTerritory(territory, user.getUsername());
            return ResponseEntity.ok(updatedTerritory);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerritory(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        territoryService.deleteTerritory(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Territory> getTerritoryById(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Territory> territory = territoryService.getTerritoryById(id, user.getUsername());
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
        List<Territory> territories = territoryService.getAllTerritoriesByUniverse(universeId);
        return ResponseEntity.ok(territories);
    }


}
