package kz.izzi.service;

import kz.izzi.service.dto.FilterDTO;
import java.util.List;

/**
 * Service Interface for managing Filter.
 */
public interface FilterService {

    /**
     * Save a filter.
     *
     * @param filterDTO the entity to save
     * @return the persisted entity
     */
    FilterDTO save(FilterDTO filterDTO);

    /**
     *  Get all the filters.
     *
     *  @return the list of entities
     */
    List<FilterDTO> findAll();

    /**
     *  Get the "id" filter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FilterDTO findOne(Long id);

    /**
     *  Delete the "id" filter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
