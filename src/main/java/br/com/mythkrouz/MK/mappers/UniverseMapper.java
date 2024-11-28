package br.com.mythkrouz.MK.mappers;

import br.com.mythkrouz.MK.dto.UniverseDTO;
import br.com.mythkrouz.MK.entities.Universe;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UniverseMapper {
    public static UniverseDTO toDTO(Universe universe) {
        if (universe == null) {
            return null;
        }

        return new UniverseDTO(
                universe.getUniverseId(),
                universe.getName(),
                universe.getDescription(),
                universe.getCreationDate(),
                universe.getCreator().getUserId()
        );
    }

    public static Universe toEntity(UniverseDTO universeDTO) {
        if (universeDTO == null) {
            return null;
        }

        Universe universe = new Universe();
        universe.setUniverseId(universeDTO.universeId());
        universe.setName(universeDTO.name());
        universe.setDescription(universeDTO.description());
        universe.setCreationDate(universeDTO.creationDate());
        //todo: creator user no service

        return universe;
    }

}
