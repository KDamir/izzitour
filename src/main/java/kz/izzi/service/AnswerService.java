package kz.izzi.service;

import kz.izzi.service.dto.AnswerDTO;
import java.util.List;

/**
 * Service Interface for managing Answer.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save
     * @return the persisted entity
     */
    AnswerDTO save(AnswerDTO answerDTO);

    /**
     *  Get all the answers.
     *
     *  @return the list of entities
     */
    List<AnswerDTO> findAll();

    /**
     *  Get the "id" answer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AnswerDTO findOne(Long id);

    /**
     *  Delete the "id" answer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
