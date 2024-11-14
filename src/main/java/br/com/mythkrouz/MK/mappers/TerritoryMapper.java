package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.TerritoryDTO;
import br.com.mythkrouz.MK.entities.Territory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TerritoryMapper {
    public static TerritoryDTO toDTO(Territory territory) {
        if (territory == null) return null;

        return new TerritoryDTO(
                territory.getTerritoryId(),
                territory.getName(),
                territory.getDescription(),
                territory.getUniverse().getUniverseId()
        );
    }

    public static Territory toEntity(TerritoryDTO territoryDTO) {
        if (territoryDTO == null) return null;

        Territory territory = new Territory();
        territory.setTerritoryId(territory.getTerritoryId());
        territory.setDescription(territory.getDescription());
        //todo: universo no service

        return territory;
    }
}
