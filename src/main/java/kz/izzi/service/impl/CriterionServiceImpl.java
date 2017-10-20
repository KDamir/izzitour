package kz.izzi.service.impl;

import kz.izzi.service.CriterionService;
import kz.izzi.domain.Criterion;
import kz.izzi.repository.CriterionRepository;
import kz.izzi.service.dto.CriterionDTO;
import kz.izzi.service.mapper.CriterionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Criterion.
 */
@Service
@Transactional
public class CriterionServiceImpl implements CriterionService{

    private final Logger log = LoggerFactory.getLogger(CriterionServiceImpl.class);

    private final CriterionRepository criterionRepository;

    private final CriterionMapper criterionMapper;

    public CriterionServiceImpl(CriterionRepository criterionRepository, CriterionMapper criterionMapper) {
        this.criterionRepository = criterionRepository;
        this.criterionMapper = criterionMapper;
    }

    /**
     * Save a criterion.
     *
     * @param criterionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CriterionDTO save(CriterionDTO criterionDTO) {
        log.debug("Request to save Criterion : {}", criterionDTO);
        Criterion criterion = criterionMapper.toEntity(criterionDTO);
        criterion = criterionRepository.save(criterion);
        return criterionMapper.toDto(criterion);
    }

    /**
     *  Get all the criteria.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CriterionDTO> findAll() {
        log.debug("Request to get all Criteria");
        return criterionRepository.findAll().stream()
            .map(criterionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one criterion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CriterionDTO findOne(Long id) {
        log.debug("Request to get Criterion : {}", id);
        Criterion criterion = criterionRepository.findOne(id);
        return criterionMapper.toDto(criterion);
    }

    /**
     *  Delete the  criterion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Criterion : {}", id);
        criterionRepository.delete(id);
    }
}
