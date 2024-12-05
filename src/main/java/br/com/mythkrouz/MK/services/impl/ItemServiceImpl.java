package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.ItemDTO;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.entities.enums.Rarity;
import br.com.mythkrouz.MK.entities.enums.Type;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.ItemMapper;
import br.com.mythkrouz.MK.repositories.CharacterRepository;
import br.com.mythkrouz.MK.repositories.ItemRepository;
import br.com.mythkrouz.MK.repositories.TerritoryRepository;
import br.com.mythkrouz.MK.repositories.UserRepository;
import br.com.mythkrouz.MK.services.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final TerritoryRepository territoryRepository;
    private final CharacterRepository characterRepository;
    private UserRepository userRepository;
    //usar o autowired no construtor vai facilitar ao explicitar p testes unitarios
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, TerritoryRepository territoryRepository, CharacterRepository characterRepository) {
        this.itemRepository = itemRepository;
        this.territoryRepository = territoryRepository;
        this.characterRepository = characterRepository;
    }

    @Transactional
    @Override
    public ItemDTO createItem(ItemDTO itemDto, String userEmail) throws EntityAlreadyExistsException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Optional<Item> existingItem = itemRepository.findByName(itemDto.name());
        if (existingItem.isPresent()) {
            throw new EntityAlreadyExistsException("Item");
        }

        if (!userId.equals(existingItem.get().getOrigin().getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("O usuário não pode criar um item cuja origem não seja de sua " +
                    "própria criação.");
        }

        Item savedItem = itemRepository.save(ItemMapper.toEntity(itemDto));
        savedItem.setOrigin(existingItem.get().getOrigin());
        savedItem.setOwners(existingItem.get().getOwners());

        return ItemMapper.toDTO(savedItem);
    }

    @Transactional
    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O item de ID: "+ id + " não foi encontrado."));

        if (!userId.equals(existingItem.getOrigin().getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a editar este item.");
        }

        if (itemDto.name() != null && !itemDto.name().trim().isEmpty()) {
            existingItem.setName(itemDto.name());
        }
        if (itemDto.description() != null && !itemDto.description().trim().isEmpty()) {
            existingItem.setDescription(itemDto.description());
        }
        if (itemDto.type() != null){
            try {
                Type type = Type.valueOf(itemDto.type().toString().toUpperCase());
                existingItem.setType(type);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo inválido fornecido para o item: " + itemDto.type());
            }
        }
        if (itemDto.rarity() != null){
            try{
                Rarity rarity = Rarity.valueOf(itemDto.rarity().toString().toUpperCase());
                existingItem.setRarity(rarity);
            }catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo inválido fornecido para o item: " + itemDto.rarity());
            }
        }
        if (itemDto.territoryId() != null){
            Territory territory = territoryRepository.findById(itemDto.territoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Território de ID: "
                            + itemDto.territoryId() + " não encontrado."));
            existingItem.setOrigin(territoryRepository.findById(itemDto.territoryId()).get());
        }

        if (itemDto.ownerIds() != null && !itemDto.ownerIds().isEmpty()){
            List<Character> validOwners = itemDto.ownerIds().stream()
                    .map(ownerId -> characterRepository.findById(ownerId)
                            .orElseThrow(() -> new EntityNotFoundException("Dono com ID: " + ownerId
                                    + " não encontrado.")))
                    .collect(Collectors.toList());
            existingItem.setOwners(validOwners);
        }

        Item updatedItem = itemRepository.save(existingItem);
        return ItemMapper.toDTO(updatedItem);
    }

    @Transactional
    @Override
    public void deleteItem(Long itemId, String userEmail) {
        Item existingItem = itemRepository.findById(itemId)
                        .orElseThrow(() -> new EntityNotFoundException("O item de ID: "+
                                itemId +" não foi encrotado."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingItem.getOrigin().getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a deletar este item.");
        }

        itemRepository.deleteById(itemId);
    }

    @Override
    public Optional<Item> getItemById(Long itemId, String userEmail) {
        Optional<Item> existingItem = itemRepository.findById(itemId);

        if (existingItem.isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingItem.get().getOrigin().getUniverse().getCreator().getUserId())) {
            throw new IllegalArgumentException("Usuário não autorizado a acessar este item.");
        }
        return existingItem;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItemsByType(String type) {
        Type validType;
        try{
            validType = Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo inválido fornecido: " + type);
        }
        return itemRepository.findAllByType(validType);
    }

    @Override
    public List<Item> getAllItemsByRarity(String rarity) {
        Rarity validRarity;
        try{
            validRarity = Rarity.valueOf(rarity.toUpperCase());
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo inválido fornecido: " + rarity);
        }
        return itemRepository.findAllByRarity(validRarity);
    }

    @Override
    public List<Item> getAllItemsByOwnerId(Long ownerId) {
        return itemRepository.findAllByOwners_CharacterId(ownerId);
    }

    @Override
    public List<Item> getAllItemsByTerritoryId(Long territoryId) {
        return itemRepository.findAllByOrigin_TerritoryId(territoryId);
    }


}
