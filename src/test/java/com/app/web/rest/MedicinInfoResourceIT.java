package com.app.web.rest;

import com.app.MedicationBackendApp;
import com.app.domain.MedicinInfo;
import com.app.repository.MedicinInfoRepository;
import com.app.service.MedicinInfoService;
import com.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MedicinInfoResource} REST controller.
 */
@SpringBootTest(classes = MedicationBackendApp.class)
public class MedicinInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMPORTANT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTANT_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_INITIAL_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PROMISED = "AAAAAAAAAA";
    private static final String UPDATED_PROMISED = "BBBBBBBBBB";

    private static final Instant DEFAULT_REFILL_INFO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REFILL_INFO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHARMACY_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_PHARMACY_NOTES = "BBBBBBBBBB";

    @Autowired
    private MedicinInfoRepository medicinInfoRepository;

    @Autowired
    private MedicinInfoService medicinInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMedicinInfoMockMvc;

    private MedicinInfo medicinInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicinInfoResource medicinInfoResource = new MedicinInfoResource(medicinInfoService);
        this.restMedicinInfoMockMvc = MockMvcBuilders.standaloneSetup(medicinInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicinInfo createEntity(EntityManager em) {
        MedicinInfo medicinInfo = new MedicinInfo()
            .name(DEFAULT_NAME)
            .importantInfo(DEFAULT_IMPORTANT_INFO)
            .usage(DEFAULT_USAGE)
            .initialCount(DEFAULT_INITIAL_COUNT)
            .promised(DEFAULT_PROMISED)
            .refillInfo(DEFAULT_REFILL_INFO)
            .pharmacyNotes(DEFAULT_PHARMACY_NOTES);
        return medicinInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicinInfo createUpdatedEntity(EntityManager em) {
        MedicinInfo medicinInfo = new MedicinInfo()
            .name(UPDATED_NAME)
            .importantInfo(UPDATED_IMPORTANT_INFO)
            .usage(UPDATED_USAGE)
            .initialCount(UPDATED_INITIAL_COUNT)
            .promised(UPDATED_PROMISED)
            .refillInfo(UPDATED_REFILL_INFO)
            .pharmacyNotes(UPDATED_PHARMACY_NOTES);
        return medicinInfo;
    }

    @BeforeEach
    public void initTest() {
        medicinInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicinInfo() throws Exception {
        int databaseSizeBeforeCreate = medicinInfoRepository.findAll().size();

        // Create the MedicinInfo
        restMedicinInfoMockMvc.perform(post("/api/medicin-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicinInfo)))
            .andExpect(status().isCreated());

        // Validate the MedicinInfo in the database
        List<MedicinInfo> medicinInfoList = medicinInfoRepository.findAll();
        assertThat(medicinInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MedicinInfo testMedicinInfo = medicinInfoList.get(medicinInfoList.size() - 1);
        assertThat(testMedicinInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicinInfo.getImportantInfo()).isEqualTo(DEFAULT_IMPORTANT_INFO);
        assertThat(testMedicinInfo.getUsage()).isEqualTo(DEFAULT_USAGE);
        assertThat(testMedicinInfo.getInitialCount()).isEqualTo(DEFAULT_INITIAL_COUNT);
        assertThat(testMedicinInfo.getPromised()).isEqualTo(DEFAULT_PROMISED);
        assertThat(testMedicinInfo.getRefillInfo()).isEqualTo(DEFAULT_REFILL_INFO);
        assertThat(testMedicinInfo.getPharmacyNotes()).isEqualTo(DEFAULT_PHARMACY_NOTES);
    }

    @Test
    @Transactional
    public void createMedicinInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicinInfoRepository.findAll().size();

        // Create the MedicinInfo with an existing ID
        medicinInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicinInfoMockMvc.perform(post("/api/medicin-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicinInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MedicinInfo in the database
        List<MedicinInfo> medicinInfoList = medicinInfoRepository.findAll();
        assertThat(medicinInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicinInfos() throws Exception {
        // Initialize the database
        medicinInfoRepository.saveAndFlush(medicinInfo);

        // Get all the medicinInfoList
        restMedicinInfoMockMvc.perform(get("/api/medicin-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicinInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].importantInfo").value(hasItem(DEFAULT_IMPORTANT_INFO)))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE)))
            .andExpect(jsonPath("$.[*].initialCount").value(hasItem(DEFAULT_INITIAL_COUNT)))
            .andExpect(jsonPath("$.[*].promised").value(hasItem(DEFAULT_PROMISED)))
            .andExpect(jsonPath("$.[*].refillInfo").value(hasItem(DEFAULT_REFILL_INFO.toString())))
            .andExpect(jsonPath("$.[*].pharmacyNotes").value(hasItem(DEFAULT_PHARMACY_NOTES)));
    }
    
    @Test
    @Transactional
    public void getMedicinInfo() throws Exception {
        // Initialize the database
        medicinInfoRepository.saveAndFlush(medicinInfo);

        // Get the medicinInfo
        restMedicinInfoMockMvc.perform(get("/api/medicin-infos/{id}", medicinInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicinInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.importantInfo").value(DEFAULT_IMPORTANT_INFO))
            .andExpect(jsonPath("$.usage").value(DEFAULT_USAGE))
            .andExpect(jsonPath("$.initialCount").value(DEFAULT_INITIAL_COUNT))
            .andExpect(jsonPath("$.promised").value(DEFAULT_PROMISED))
            .andExpect(jsonPath("$.refillInfo").value(DEFAULT_REFILL_INFO.toString()))
            .andExpect(jsonPath("$.pharmacyNotes").value(DEFAULT_PHARMACY_NOTES));
    }

    @Test
    @Transactional
    public void getNonExistingMedicinInfo() throws Exception {
        // Get the medicinInfo
        restMedicinInfoMockMvc.perform(get("/api/medicin-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicinInfo() throws Exception {
        // Initialize the database
        medicinInfoService.save(medicinInfo);

        int databaseSizeBeforeUpdate = medicinInfoRepository.findAll().size();

        // Update the medicinInfo
        MedicinInfo updatedMedicinInfo = medicinInfoRepository.findById(medicinInfo.getId()).get();
        // Disconnect from session so that the updates on updatedMedicinInfo are not directly saved in db
        em.detach(updatedMedicinInfo);
        updatedMedicinInfo
            .name(UPDATED_NAME)
            .importantInfo(UPDATED_IMPORTANT_INFO)
            .usage(UPDATED_USAGE)
            .initialCount(UPDATED_INITIAL_COUNT)
            .promised(UPDATED_PROMISED)
            .refillInfo(UPDATED_REFILL_INFO)
            .pharmacyNotes(UPDATED_PHARMACY_NOTES);

        restMedicinInfoMockMvc.perform(put("/api/medicin-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicinInfo)))
            .andExpect(status().isOk());

        // Validate the MedicinInfo in the database
        List<MedicinInfo> medicinInfoList = medicinInfoRepository.findAll();
        assertThat(medicinInfoList).hasSize(databaseSizeBeforeUpdate);
        MedicinInfo testMedicinInfo = medicinInfoList.get(medicinInfoList.size() - 1);
        assertThat(testMedicinInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicinInfo.getImportantInfo()).isEqualTo(UPDATED_IMPORTANT_INFO);
        assertThat(testMedicinInfo.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testMedicinInfo.getInitialCount()).isEqualTo(UPDATED_INITIAL_COUNT);
        assertThat(testMedicinInfo.getPromised()).isEqualTo(UPDATED_PROMISED);
        assertThat(testMedicinInfo.getRefillInfo()).isEqualTo(UPDATED_REFILL_INFO);
        assertThat(testMedicinInfo.getPharmacyNotes()).isEqualTo(UPDATED_PHARMACY_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicinInfo() throws Exception {
        int databaseSizeBeforeUpdate = medicinInfoRepository.findAll().size();

        // Create the MedicinInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicinInfoMockMvc.perform(put("/api/medicin-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicinInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MedicinInfo in the database
        List<MedicinInfo> medicinInfoList = medicinInfoRepository.findAll();
        assertThat(medicinInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicinInfo() throws Exception {
        // Initialize the database
        medicinInfoService.save(medicinInfo);

        int databaseSizeBeforeDelete = medicinInfoRepository.findAll().size();

        // Delete the medicinInfo
        restMedicinInfoMockMvc.perform(delete("/api/medicin-infos/{id}", medicinInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicinInfo> medicinInfoList = medicinInfoRepository.findAll();
        assertThat(medicinInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
