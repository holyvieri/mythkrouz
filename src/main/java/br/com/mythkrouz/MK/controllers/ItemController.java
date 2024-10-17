package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            Item createdItem = itemService.createItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setItemId(id);
        try {
            Item updatedItem = itemService.updateItem(item);
            return ResponseEntity.ok(updatedItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //TENTATIVA DE NOTIFICAR DIREITO A QUESTAO DA LISTA VAZIA
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Item>> getItemsByType(@PathVariable String type) {
        List<Item> items = itemService.getItemsByType(type);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Item>> getItemsByRarity(@PathVariable String rarity) {
        List<Item> items = itemService.getItemsByRarity(rarity);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Item>> getItemsByOwnerId(@PathVariable Long ownerId) {
        List<Item> items = itemService.getItemsByOwnerId(ownerId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/universe/{universeId}")
    public ResponseEntity<List<Item>> getItemsByUniverseId(@PathVariable Long universeId) {
        List<Item> items = itemService.getItemsByUniverseId(universeId);
        return ResponseEntity.ok(items);
    }






}
