package kz.izzi.service.impl;

import kz.izzi.service.AnswerService;
import kz.izzi.domain.Answer;
import kz.izzi.repository.AnswerRepository;
import kz.izzi.service.dto.AnswerDTO;
import kz.izzi.service.mapper.AnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Answer.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.toEntity(answerDTO);
        answer = answerRepository.save(answer);
        return answerMapper.toDto(answer);
    }

    /**
     *  Get all the answers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnswerDTO> findAll() {
        log.debug("Request to get all Answers");
        return answerRepository.findAllWithEagerRelationships().stream()
            .map(answerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one answer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnswerDTO findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        Answer answer = answerRepository.findOneWithEagerRelationships(id);
        return answerMapper.toDto(answer);
    }

    /**
     *  Delete the  answer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.delete(id);
    }
}
