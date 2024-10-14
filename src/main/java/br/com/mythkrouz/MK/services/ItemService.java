package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public Item createItem(Item item) throws EntityAlreadyExistsException;
    public Item updateItem(Item item);
    public void deleteItem(Long id);
    public Optional<Item> getItemById(Long id);
    public List<Item> getAllItems();
    public List<Item> getItemsByType(String type);
    public List<Item> getItemsByRarity(String rarity);
    public List<Item> getItemsByOwnerId(Long ownerId);
    public List<Item> getItemsByUniverseId(Long universeId);
}
