package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.FilterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Filter and its DTO FilterDTO.
 */
@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface FilterMapper extends EntityMapper<FilterDTO, Filter> {

    @Mapping(source = "answer.id", target = "answerId")
    FilterDTO toDto(Filter filter); 

    @Mapping(source = "answerId", target = "answer")
    @Mapping(target = "criteria", ignore = true)
    Filter toEntity(FilterDTO filterDTO);

    default Filter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Filter filter = new Filter();
        filter.setId(id);
        return filter;
    }
}
