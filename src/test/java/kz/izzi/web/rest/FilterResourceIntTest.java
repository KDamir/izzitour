package kz.izzi.web.rest;

import kz.izzi.EztourApp;

import kz.izzi.domain.Filter;
import kz.izzi.repository.FilterRepository;
import kz.izzi.service.FilterService;
import kz.izzi.service.dto.FilterDTO;
import kz.izzi.service.mapper.FilterMapper;
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

/**
 * Test class for the FilterResource REST controller.
 *
 * @see FilterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EztourApp.class)
public class FilterResourceIntTest {

    private static final Double DEFAULT_PROBABILITY = 1D;
    private static final Double UPDATED_PROBABILITY = 2D;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private FilterMapper filterMapper;

    @Autowired
    private FilterService filterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilterMockMvc;

    private Filter filter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilterResource filterResource = new FilterResource(filterService);
        this.restFilterMockMvc = MockMvcBuilders.standaloneSetup(filterResource)
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
    public static Filter createEntity(EntityManager em) {
        Filter filter = new Filter()
            .probability(DEFAULT_PROBABILITY);
        return filter;
    }

    @Before
    public void initTest() {
        filter = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilter() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter
        FilterDTO filterDTO = filterMapper.toDto(filter);
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterDTO)))
            .andExpect(status().isCreated());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate + 1);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
    }

    @Test
    @Transactional
    public void createFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter with an existing ID
        filter.setId(1L);
        FilterDTO filterDTO = filterMapper.toDto(filter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilters() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList
        restFilterMockMvc.perform(get("/api/filters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filter.getId().intValue())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", filter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filter.getId().intValue()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFilter() throws Exception {
        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);
        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Update the filter
        Filter updatedFilter = filterRepository.findOne(filter.getId());
        updatedFilter
            .probability(UPDATED_PROBABILITY);
        FilterDTO filterDTO = filterMapper.toDto(updatedFilter);

        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterDTO)))
            .andExpect(status().isOk());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingFilter() throws Exception {
        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Create the Filter
        FilterDTO filterDTO = filterMapper.toDto(filter);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filterDTO)))
            .andExpect(status().isCreated());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);
        int databaseSizeBeforeDelete = filterRepository.findAll().size();

        // Get the filter
        restFilterMockMvc.perform(delete("/api/filters/{id}", filter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filter.class);
        Filter filter1 = new Filter();
        filter1.setId(1L);
        Filter filter2 = new Filter();
        filter2.setId(filter1.getId());
        assertThat(filter1).isEqualTo(filter2);
        filter2.setId(2L);
        assertThat(filter1).isNotEqualTo(filter2);
        filter1.setId(null);
        assertThat(filter1).isNotEqualTo(filter2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilterDTO.class);
        FilterDTO filterDTO1 = new FilterDTO();
        filterDTO1.setId(1L);
        FilterDTO filterDTO2 = new FilterDTO();
        assertThat(filterDTO1).isNotEqualTo(filterDTO2);
        filterDTO2.setId(filterDTO1.getId());
        assertThat(filterDTO1).isEqualTo(filterDTO2);
        filterDTO2.setId(2L);
        assertThat(filterDTO1).isNotEqualTo(filterDTO2);
        filterDTO1.setId(null);
        assertThat(filterDTO1).isNotEqualTo(filterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(filterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(filterMapper.fromId(null)).isNull();
    }
}
