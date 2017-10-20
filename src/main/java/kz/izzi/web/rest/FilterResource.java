package kz.izzi.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.izzi.service.FilterService;
import kz.izzi.web.rest.errors.BadRequestAlertException;
import kz.izzi.web.rest.util.HeaderUtil;
import kz.izzi.service.dto.FilterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Filter.
 */
@RestController
@RequestMapping("/api")
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(FilterResource.class);

    private static final String ENTITY_NAME = "filter";

    private final FilterService filterService;

    public FilterResource(FilterService filterService) {
        this.filterService = filterService;
    }

    /**
     * POST  /filters : Create a new filter.
     *
     * @param filterDTO the filterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filterDTO, or with status 400 (Bad Request) if the filter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filters")
    @Timed
    public ResponseEntity<FilterDTO> createFilter(@RequestBody FilterDTO filterDTO) throws URISyntaxException {
        log.debug("REST request to save Filter : {}", filterDTO);
        if (filterDTO.getId() != null) {
            throw new BadRequestAlertException("A new filter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilterDTO result = filterService.save(filterDTO);
        return ResponseEntity.created(new URI("/api/filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filters : Updates an existing filter.
     *
     * @param filterDTO the filterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filterDTO,
     * or with status 400 (Bad Request) if the filterDTO is not valid,
     * or with status 500 (Internal Server Error) if the filterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filters")
    @Timed
    public ResponseEntity<FilterDTO> updateFilter(@RequestBody FilterDTO filterDTO) throws URISyntaxException {
        log.debug("REST request to update Filter : {}", filterDTO);
        if (filterDTO.getId() == null) {
            return createFilter(filterDTO);
        }
        FilterDTO result = filterService.save(filterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filters : get all the filters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filters in body
     */
    @GetMapping("/filters")
    @Timed
    public List<FilterDTO> getAllFilters() {
        log.debug("REST request to get all Filters");
        return filterService.findAll();
        }

    /**
     * GET  /filters/:id : get the "id" filter.
     *
     * @param id the id of the filterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filters/{id}")
    @Timed
    public ResponseEntity<FilterDTO> getFilter(@PathVariable Long id) {
        log.debug("REST request to get Filter : {}", id);
        FilterDTO filterDTO = filterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filterDTO));
    }

    /**
     * DELETE  /filters/:id : delete the "id" filter.
     *
     * @param id the id of the filterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filters/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        log.debug("REST request to delete Filter : {}", id);
        filterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
