package kz.izzi.service;

import kz.izzi.service.dto.QuestionDTO;
import java.util.List;

/**
 * Service Interface for managing Question.
 */
public interface QuestionService {

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    QuestionDTO save(QuestionDTO questionDTO);

    /**
     *  Get all the questions.
     *
     *  @return the list of entities
     */
    List<QuestionDTO> findAll();

    /**
     *  Get the "id" question.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuestionDTO findOne(Long id);

    /**
     *  Delete the "id" question.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
