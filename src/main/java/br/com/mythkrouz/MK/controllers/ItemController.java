package br.com.mythkrouz.MK.controllers;

import br.com.mythkrouz.MK.dto.ItemDTO;
import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.services.ItemService;
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
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody Item item) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            ItemDTO createdItem = itemService.createItem(item, user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDto) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            ItemDTO updatedItem = itemService.updateItem(id, itemDto, user.getUsername());
            return ResponseEntity.ok(updatedItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        itemService.deleteItem(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Item> item = itemService.getItemById(id, user.getUsername());
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //TENTATIVA DE NOTIFICAR DIREITO A QUESTAO DA LISTA VAZIA
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Item>> getItemsByType(@PathVariable String type) {
        List<Item> items = itemService.getAllItemsByType(type);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Item>> getItemsByRarity(@PathVariable String rarity) {
        List<Item> items = itemService.getAllItemsByRarity(rarity);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Item>> getItemsByOwnerId(@PathVariable Long ownerId) {
        List<Item> items = itemService.getAllItemsByOwnerId(ownerId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/territory/{territoryId}")
    public ResponseEntity<List<Item>> getItemsByTerritoryId(@PathVariable Long territoryId) {
        List<Item> items = itemService.getAllItemsByTerritoryId(territoryId);
        return ResponseEntity.ok(items);
    }
}
