package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.PlaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Place and its DTO PlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaceMapper extends EntityMapper<PlaceDTO, Place> {

    

    @Mapping(target = "categories", ignore = true)
    Place toEntity(PlaceDTO placeDTO);

    default Place fromId(Long id) {
        if (id == null) {
            return null;
        }
        Place place = new Place();
        place.setId(id);
        return place;
    }
}
