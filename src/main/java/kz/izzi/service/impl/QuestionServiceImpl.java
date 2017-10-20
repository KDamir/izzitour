package kz.izzi.service.impl;

import kz.izzi.service.QuestionService;
import kz.izzi.domain.Question;
import kz.izzi.repository.QuestionRepository;
import kz.izzi.service.dto.QuestionDTO;
import kz.izzi.service.mapper.QuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Question.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    /**
     *  Get all the questions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDTO> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll().stream()
            .map(questionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one question by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuestionDTO findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        Question question = questionRepository.findOne(id);
        return questionMapper.toDto(question);
    }

    /**
     *  Delete the  question by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.delete(id);
    }
}
