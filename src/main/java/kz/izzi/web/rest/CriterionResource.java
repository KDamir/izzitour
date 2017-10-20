package kz.izzi.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.izzi.service.CriterionService;
import kz.izzi.web.rest.errors.BadRequestAlertException;
import kz.izzi.web.rest.util.HeaderUtil;
import kz.izzi.service.dto.CriterionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Criterion.
 */
@RestController
@RequestMapping("/api")
public class CriterionResource {

    private final Logger log = LoggerFactory.getLogger(CriterionResource.class);

    private static final String ENTITY_NAME = "criterion";

    private final CriterionService criterionService;

    public CriterionResource(CriterionService criterionService) {
        this.criterionService = criterionService;
    }

    /**
     * POST  /criteria : Create a new criterion.
     *
     * @param criterionDTO the criterionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new criterionDTO, or with status 400 (Bad Request) if the criterion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/criteria")
    @Timed
    public ResponseEntity<CriterionDTO> createCriterion(@Valid @RequestBody CriterionDTO criterionDTO) throws URISyntaxException {
        log.debug("REST request to save Criterion : {}", criterionDTO);
        if (criterionDTO.getId() != null) {
            throw new BadRequestAlertException("A new criterion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CriterionDTO result = criterionService.save(criterionDTO);
        return ResponseEntity.created(new URI("/api/criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /criteria : Updates an existing criterion.
     *
     * @param criterionDTO the criterionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated criterionDTO,
     * or with status 400 (Bad Request) if the criterionDTO is not valid,
     * or with status 500 (Internal Server Error) if the criterionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/criteria")
    @Timed
    public ResponseEntity<CriterionDTO> updateCriterion(@Valid @RequestBody CriterionDTO criterionDTO) throws URISyntaxException {
        log.debug("REST request to update Criterion : {}", criterionDTO);
        if (criterionDTO.getId() == null) {
            return createCriterion(criterionDTO);
        }
        CriterionDTO result = criterionService.save(criterionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, criterionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /criteria : get all the criteria.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of criteria in body
     */
    @GetMapping("/criteria")
    @Timed
    public List<CriterionDTO> getAllCriteria() {
        log.debug("REST request to get all Criteria");
        return criterionService.findAll();
        }

    /**
     * GET  /criteria/:id : get the "id" criterion.
     *
     * @param id the id of the criterionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the criterionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/criteria/{id}")
    @Timed
    public ResponseEntity<CriterionDTO> getCriterion(@PathVariable Long id) {
        log.debug("REST request to get Criterion : {}", id);
        CriterionDTO criterionDTO = criterionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(criterionDTO));
    }

    /**
     * DELETE  /criteria/:id : delete the "id" criterion.
     *
     * @param id the id of the criterionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/criteria/{id}")
    @Timed
    public ResponseEntity<Void> deleteCriterion(@PathVariable Long id) {
        log.debug("REST request to delete Criterion : {}", id);
        criterionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
