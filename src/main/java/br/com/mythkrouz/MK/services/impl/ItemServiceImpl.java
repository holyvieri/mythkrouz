package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.ItemRepository;
import br.com.mythkrouz.MK.services.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    //usar o autowired no construtor vai facilitar ao explicitar p testes unitarios
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public Item createItem(Item item) throws EntityAlreadyExistsException {
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do item não pode ser nulo ou vazio.");
        }

        Optional<Item> existingItem = itemRepository.findItemByName(item.getName());
        if (existingItem.isPresent()) {
            throw new EntityAlreadyExistsException("Item");
        }
        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public Item updateItem(Item item) {
        Optional<Item> existingItem = itemRepository.findById(item.getItemId());
        if (existingItem.isPresent()) {
            return itemRepository.save(item);
        }else{
            throw new EntityNotFoundException("Item");
        }
    }

    @Transactional
    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public Optional<Item> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByType(String type) {
        return itemRepository.findByType(type);
    }

    @Override
    public List<Item> getItemsByRarity(String rarity) {
        return itemRepository.findByRarity(rarity);
    }

    @Override
    public List<Item> getItemsByOwnerId(Long ownerId) {
        return itemRepository.findByOwner_CharacterId(ownerId);
    }

    @Override
    public List<Item> getItemsByUniverseId(Long universeId) {
        return itemRepository.findByUniverse_UniverseId(universeId);
    }
}
