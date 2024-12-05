package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.ItemDTO;
import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    public ItemDTO createItem(ItemDTO item, String userEmail) throws EntityAlreadyExistsException;
    public ItemDTO updateItem(Long id, ItemDTO item, String userEmail);
    public void deleteItem(Long itemId, String userEmail);
    public Optional<Item> getItemById(Long itemId, String userEmail);
    public List<Item> getAllItems();
    public List<Item> getAllItemsByType(String type);
    public List<Item> getAllItemsByRarity(String rarity);
    public List<Item> getAllItemsByOwnerId(Long ownerId);
    public List<Item> getAllItemsByTerritoryId(Long territoryId);
}
