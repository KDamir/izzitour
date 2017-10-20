package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {PlaceMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "place.id", target = "placeId")
    CategoryDTO toDto(Category category); 

    @Mapping(source = "placeId", target = "place")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
