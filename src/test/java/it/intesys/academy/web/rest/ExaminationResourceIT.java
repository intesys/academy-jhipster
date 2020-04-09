package it.intesys.academy.web.rest;

import it.intesys.academy.HipsterAcademyApp;
import it.intesys.academy.domain.Examination;
import it.intesys.academy.domain.Patient;
import it.intesys.academy.repository.ExaminationRepository;
import it.intesys.academy.service.ExaminationService;
import it.intesys.academy.service.dto.ExaminationDTO;
import it.intesys.academy.service.mapper.ExaminationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static it.intesys.academy.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExaminationResource} REST controller.
 */
@SpringBootTest(classes = HipsterAcademyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ExaminationResourceIT {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_DIASTOLIC_PRESSURE = 1;
    private static final Integer UPDATED_DIASTOLIC_PRESSURE = 2;

    private static final Integer DEFAULT_SYSTOLIC_PRESSURE = 1;
    private static final Integer UPDATED_SYSTOLIC_PRESSURE = 2;

    private static final ZonedDateTime DEFAULT_EXAMINATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXAMINATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private ExaminationMapper examinationMapper;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExaminationMockMvc;

    private Examination examination;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examination createEntity(EntityManager em) {
        Examination examination = new Examination()
            .weight(DEFAULT_WEIGHT)
            .height(DEFAULT_HEIGHT)
            .diastolicPressure(DEFAULT_DIASTOLIC_PRESSURE)
            .systolicPressure(DEFAULT_SYSTOLIC_PRESSURE)
            .examinationDate(DEFAULT_EXAMINATION_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        examination.setPatient(patient);
        return examination;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examination createUpdatedEntity(EntityManager em) {
        Examination examination = new Examination()
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .diastolicPressure(UPDATED_DIASTOLIC_PRESSURE)
            .systolicPressure(UPDATED_SYSTOLIC_PRESSURE)
            .examinationDate(UPDATED_EXAMINATION_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        examination.setPatient(patient);
        return examination;
    }

    @BeforeEach
    public void initTest() {
        examination = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamination() throws Exception {
        int databaseSizeBeforeCreate = examinationRepository.findAll().size();

        // Create the Examination
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);
        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isCreated());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeCreate + 1);
        Examination testExamination = examinationList.get(examinationList.size() - 1);
        assertThat(testExamination.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testExamination.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testExamination.getDiastolicPressure()).isEqualTo(DEFAULT_DIASTOLIC_PRESSURE);
        assertThat(testExamination.getSystolicPressure()).isEqualTo(DEFAULT_SYSTOLIC_PRESSURE);
        assertThat(testExamination.getExaminationDate()).isEqualTo(DEFAULT_EXAMINATION_DATE);
        assertThat(testExamination.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testExamination.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createExaminationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examinationRepository.findAll().size();

        // Create the Examination with an existing ID
        examination.setId(1L);
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinationRepository.findAll().size();
        // set the field null
        examination.setWeight(null);

        // Create the Examination, which fails.
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinationRepository.findAll().size();
        // set the field null
        examination.setHeight(null);

        // Create the Examination, which fails.
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiastolicPressureIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinationRepository.findAll().size();
        // set the field null
        examination.setDiastolicPressure(null);

        // Create the Examination, which fails.
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystolicPressureIsRequired() throws Exception {
        int databaseSizeBeforeTest = examinationRepository.findAll().size();
        // set the field null
        examination.setSystolicPressure(null);

        // Create the Examination, which fails.
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        restExaminationMockMvc.perform(post("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExaminations() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        // Get all the examinationList
        restExaminationMockMvc.perform(get("/api/examinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examination.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].diastolicPressure").value(hasItem(DEFAULT_DIASTOLIC_PRESSURE)))
            .andExpect(jsonPath("$.[*].systolicPressure").value(hasItem(DEFAULT_SYSTOLIC_PRESSURE)))
            .andExpect(jsonPath("$.[*].examinationDate").value(hasItem(sameInstant(DEFAULT_EXAMINATION_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        // Get the examination
        restExaminationMockMvc.perform(get("/api/examinations/{id}", examination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examination.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.diastolicPressure").value(DEFAULT_DIASTOLIC_PRESSURE))
            .andExpect(jsonPath("$.systolicPressure").value(DEFAULT_SYSTOLIC_PRESSURE))
            .andExpect(jsonPath("$.examinationDate").value(sameInstant(DEFAULT_EXAMINATION_DATE)))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingExamination() throws Exception {
        // Get the examination
        restExaminationMockMvc.perform(get("/api/examinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        int databaseSizeBeforeUpdate = examinationRepository.findAll().size();

        // Update the examination
        Examination updatedExamination = examinationRepository.findById(examination.getId()).get();
        // Disconnect from session so that the updates on updatedExamination are not directly saved in db
        em.detach(updatedExamination);
        updatedExamination
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .diastolicPressure(UPDATED_DIASTOLIC_PRESSURE)
            .systolicPressure(UPDATED_SYSTOLIC_PRESSURE)
            .examinationDate(UPDATED_EXAMINATION_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        ExaminationDTO examinationDTO = examinationMapper.toDto(updatedExamination);

        restExaminationMockMvc.perform(put("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isOk());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeUpdate);
        Examination testExamination = examinationList.get(examinationList.size() - 1);
        assertThat(testExamination.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testExamination.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testExamination.getDiastolicPressure()).isEqualTo(UPDATED_DIASTOLIC_PRESSURE);
        assertThat(testExamination.getSystolicPressure()).isEqualTo(UPDATED_SYSTOLIC_PRESSURE);
        assertThat(testExamination.getExaminationDate()).isEqualTo(UPDATED_EXAMINATION_DATE);
        assertThat(testExamination.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testExamination.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamination() throws Exception {
        int databaseSizeBeforeUpdate = examinationRepository.findAll().size();

        // Create the Examination
        ExaminationDTO examinationDTO = examinationMapper.toDto(examination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExaminationMockMvc.perform(put("/api/examinations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Examination in the database
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamination() throws Exception {
        // Initialize the database
        examinationRepository.saveAndFlush(examination);

        int databaseSizeBeforeDelete = examinationRepository.findAll().size();

        // Delete the examination
        restExaminationMockMvc.perform(delete("/api/examinations/{id}", examination.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Examination> examinationList = examinationRepository.findAll();
        assertThat(examinationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
