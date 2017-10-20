package kz.izzi.service.mapper;

import kz.izzi.domain.*;
import kz.izzi.service.dto.CriterionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Criterion and its DTO CriterionDTO.
 */
@Mapper(componentModel = "spring", uses = {FilterMapper.class})
public interface CriterionMapper extends EntityMapper<CriterionDTO, Criterion> {

    @Mapping(source = "filter.id", target = "filterId")
    CriterionDTO toDto(Criterion criterion); 

    @Mapping(source = "filterId", target = "filter")
    Criterion toEntity(CriterionDTO criterionDTO);

    default Criterion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Criterion criterion = new Criterion();
        criterion.setId(id);
        return criterion;
    }
}
