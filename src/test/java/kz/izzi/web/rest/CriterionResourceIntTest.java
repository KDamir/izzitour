package kz.izzi.web.rest;

import kz.izzi.EztourApp;

import kz.izzi.domain.Criterion;
import kz.izzi.repository.CriterionRepository;
import kz.izzi.service.CriterionService;
import kz.izzi.service.dto.CriterionDTO;
import kz.izzi.service.mapper.CriterionMapper;
import kz.izzi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kz.izzi.domain.enumeration.TypeEnum;
/**
 * Test class for the CriterionResource REST controller.
 *
 * @see CriterionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EztourApp.class)
public class CriterionResourceIntTest {

    private static final TypeEnum DEFAULT_TYPE = TypeEnum.LAT;
    private static final TypeEnum UPDATED_TYPE = TypeEnum.LON;

    private static final String DEFAULT_MAX_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MAX_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MIN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MIN_VALUE = "BBBBBBBBBB";

    @Autowired
    private CriterionRepository criterionRepository;

    @Autowired
    private CriterionMapper criterionMapper;

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCriterionMockMvc;

    private Criterion criterion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CriterionResource criterionResource = new CriterionResource(criterionService);
        this.restCriterionMockMvc = MockMvcBuilders.standaloneSetup(criterionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Criterion createEntity(EntityManager em) {
        Criterion criterion = new Criterion()
            .type(DEFAULT_TYPE)
            .maxValue(DEFAULT_MAX_VALUE)
            .minValue(DEFAULT_MIN_VALUE);
        return criterion;
    }

    @Before
    public void initTest() {
        criterion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCriterion() throws Exception {
        int databaseSizeBeforeCreate = criterionRepository.findAll().size();

        // Create the Criterion
        CriterionDTO criterionDTO = criterionMapper.toDto(criterion);
        restCriterionMockMvc.perform(post("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criterionDTO)))
            .andExpect(status().isCreated());

        // Validate the Criterion in the database
        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeCreate + 1);
        Criterion testCriterion = criterionList.get(criterionList.size() - 1);
        assertThat(testCriterion.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCriterion.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testCriterion.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
    }

    @Test
    @Transactional
    public void createCriterionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = criterionRepository.findAll().size();

        // Create the Criterion with an existing ID
        criterion.setId(1L);
        CriterionDTO criterionDTO = criterionMapper.toDto(criterion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriterionMockMvc.perform(post("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criterionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Criterion in the database
        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMinValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = criterionRepository.findAll().size();
        // set the field null
        criterion.setMinValue(null);

        // Create the Criterion, which fails.
        CriterionDTO criterionDTO = criterionMapper.toDto(criterion);

        restCriterionMockMvc.perform(post("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criterionDTO)))
            .andExpect(status().isBadRequest());

        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCriteria() throws Exception {
        // Initialize the database
        criterionRepository.saveAndFlush(criterion);

        // Get all the criterionList
        restCriterionMockMvc.perform(get("/api/criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criterion.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.toString())))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCriterion() throws Exception {
        // Initialize the database
        criterionRepository.saveAndFlush(criterion);

        // Get the criterion
        restCriterionMockMvc.perform(get("/api/criteria/{id}", criterion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(criterion.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.toString()))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCriterion() throws Exception {
        // Get the criterion
        restCriterionMockMvc.perform(get("/api/criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCriterion() throws Exception {
        // Initialize the database
        criterionRepository.saveAndFlush(criterion);
        int databaseSizeBeforeUpdate = criterionRepository.findAll().size();

        // Update the criterion
        Criterion updatedCriterion = criterionRepository.findOne(criterion.getId());
        updatedCriterion
            .type(UPDATED_TYPE)
            .maxValue(UPDATED_MAX_VALUE)
            .minValue(UPDATED_MIN_VALUE);
        CriterionDTO criterionDTO = criterionMapper.toDto(updatedCriterion);

        restCriterionMockMvc.perform(put("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criterionDTO)))
            .andExpect(status().isOk());

        // Validate the Criterion in the database
        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeUpdate);
        Criterion testCriterion = criterionList.get(criterionList.size() - 1);
        assertThat(testCriterion.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCriterion.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testCriterion.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCriterion() throws Exception {
        int databaseSizeBeforeUpdate = criterionRepository.findAll().size();

        // Create the Criterion
        CriterionDTO criterionDTO = criterionMapper.toDto(criterion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCriterionMockMvc.perform(put("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criterionDTO)))
            .andExpect(status().isCreated());

        // Validate the Criterion in the database
        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCriterion() throws Exception {
        // Initialize the database
        criterionRepository.saveAndFlush(criterion);
        int databaseSizeBeforeDelete = criterionRepository.findAll().size();

        // Get the criterion
        restCriterionMockMvc.perform(delete("/api/criteria/{id}", criterion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Criterion> criterionList = criterionRepository.findAll();
        assertThat(criterionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Criterion.class);
        Criterion criterion1 = new Criterion();
        criterion1.setId(1L);
        Criterion criterion2 = new Criterion();
        criterion2.setId(criterion1.getId());
        assertThat(criterion1).isEqualTo(criterion2);
        criterion2.setId(2L);
        assertThat(criterion1).isNotEqualTo(criterion2);
        criterion1.setId(null);
        assertThat(criterion1).isNotEqualTo(criterion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CriterionDTO.class);
        CriterionDTO criterionDTO1 = new CriterionDTO();
        criterionDTO1.setId(1L);
        CriterionDTO criterionDTO2 = new CriterionDTO();
        assertThat(criterionDTO1).isNotEqualTo(criterionDTO2);
        criterionDTO2.setId(criterionDTO1.getId());
        assertThat(criterionDTO1).isEqualTo(criterionDTO2);
        criterionDTO2.setId(2L);
        assertThat(criterionDTO1).isNotEqualTo(criterionDTO2);
        criterionDTO1.setId(null);
        assertThat(criterionDTO1).isNotEqualTo(criterionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(criterionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(criterionMapper.fromId(null)).isNull();
    }
}
