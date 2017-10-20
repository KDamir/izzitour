package kz.izzi.service.impl;

import kz.izzi.service.FilterService;
import kz.izzi.domain.Filter;
import kz.izzi.repository.FilterRepository;
import kz.izzi.service.dto.FilterDTO;
import kz.izzi.service.mapper.FilterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Filter.
 */
@Service
@Transactional
public class FilterServiceImpl implements FilterService{

    private final Logger log = LoggerFactory.getLogger(FilterServiceImpl.class);

    private final FilterRepository filterRepository;

    private final FilterMapper filterMapper;

    public FilterServiceImpl(FilterRepository filterRepository, FilterMapper filterMapper) {
        this.filterRepository = filterRepository;
        this.filterMapper = filterMapper;
    }

    /**
     * Save a filter.
     *
     * @param filterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FilterDTO save(FilterDTO filterDTO) {
        log.debug("Request to save Filter : {}", filterDTO);
        Filter filter = filterMapper.toEntity(filterDTO);
        filter = filterRepository.save(filter);
        return filterMapper.toDto(filter);
    }

    /**
     *  Get all the filters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FilterDTO> findAll() {
        log.debug("Request to get all Filters");
        return filterRepository.findAll().stream()
            .map(filterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one filter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FilterDTO findOne(Long id) {
        log.debug("Request to get Filter : {}", id);
        Filter filter = filterRepository.findOne(id);
        return filterMapper.toDto(filter);
    }

    /**
     *  Delete the  filter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Filter : {}", id);
        filterRepository.delete(id);
    }
}
