package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.ItemDTO;
import br.com.mythkrouz.MK.entities.Item;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        if (item == null) return null;

        return new ItemDTO(
                item.getItemId(),
                item.getName(),
                item.getDescription(),
                item.getType(),
                item.getRarity(),
                item.getOrigin().getTerritoryId(),
                item.getOwners().stream()
                        .map(character -> character.getCharacterId())
                        .collect(Collectors.toList())
        );
    }

    public static Item toEntity(ItemDTO dto) {
        if (dto == null) return null;

        Item item = new Item();
        item.setItemId(dto.itemId());
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setType(dto.type());
        item.setRarity(dto.rarity());

        //TODO: territorio e characters service

        return item;
    }
}
