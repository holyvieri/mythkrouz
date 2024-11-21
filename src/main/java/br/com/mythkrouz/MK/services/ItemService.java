package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public Item createItem(Item item) throws EntityAlreadyExistsException;
    public Item updateItem(Item item);
    public void deleteItem(Long itemId);
    public Optional<Item> getItemById(Long itemId);
    public List<Item> getAllItems();
    public List<Item> getAllItemsByType(String type);
    public List<Item> getAllItemsByRarity(String rarity);
    public List<Item> getAllItemsByOwnerId(Long ownerId);
    public List<Item> getAllItemsByTerritoryId(Long territoryId);
}
