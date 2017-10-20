package kz.izzi.service;

import kz.izzi.service.dto.CriterionDTO;
import java.util.List;

/**
 * Service Interface for managing Criterion.
 */
public interface CriterionService {

    /**
     * Save a criterion.
     *
     * @param criterionDTO the entity to save
     * @return the persisted entity
     */
    CriterionDTO save(CriterionDTO criterionDTO);

    /**
     *  Get all the criteria.
     *
     *  @return the list of entities
     */
    List<CriterionDTO> findAll();

    /**
     *  Get the "id" criterion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CriterionDTO findOne(Long id);

    /**
     *  Delete the "id" criterion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
