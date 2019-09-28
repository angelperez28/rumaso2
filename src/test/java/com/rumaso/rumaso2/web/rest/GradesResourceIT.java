package com.rumaso.rumaso2.web.rest;

import com.rumaso.rumaso2.Rumaso2App;
import com.rumaso.rumaso2.domain.Grades;
import com.rumaso.rumaso2.repository.GradesRepository;
import com.rumaso.rumaso2.service.GradesService;
import com.rumaso.rumaso2.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.rumaso.rumaso2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GradesResource} REST controller.
 */
@SpringBootTest(classes = Rumaso2App.class)
public class GradesResourceIT {

    private static final Integer DEFAULT_A_NUMBER = 1;
    private static final Integer UPDATED_A_NUMBER = 2;
    private static final Integer SMALLER_A_NUMBER = 1 - 1;

    private static final Integer DEFAULT_B_NUMBER = 1;
    private static final Integer UPDATED_B_NUMBER = 2;
    private static final Integer SMALLER_B_NUMBER = 1 - 1;

    private static final Integer DEFAULT_C_NUMBER = 1;
    private static final Integer UPDATED_C_NUMBER = 2;
    private static final Integer SMALLER_C_NUMBER = 1 - 1;

    private static final Integer DEFAULT_D_NUMBER = 1;
    private static final Integer UPDATED_D_NUMBER = 2;
    private static final Integer SMALLER_D_NUMBER = 1 - 1;

    private static final Integer DEFAULT_F_NUMBER = 1;
    private static final Integer UPDATED_F_NUMBER = 2;
    private static final Integer SMALLER_F_NUMBER = 1 - 1;

    private static final Integer DEFAULT_W_NUMBER = 1;
    private static final Integer UPDATED_W_NUMBER = 2;
    private static final Integer SMALLER_W_NUMBER = 1 - 1;

    private static final Integer DEFAULT_STUDENT_NUMBER = 1;
    private static final Integer UPDATED_STUDENT_NUMBER = 2;
    private static final Integer SMALLER_STUDENT_NUMBER = 1 - 1;

    @Autowired
    private GradesRepository gradesRepository;

    @Autowired
    private GradesService gradesService;

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

    private MockMvc restGradesMockMvc;

    private Grades grades;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GradesResource gradesResource = new GradesResource(gradesService);
        this.restGradesMockMvc = MockMvcBuilders.standaloneSetup(gradesResource)
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
    public static Grades createEntity(EntityManager em) {
        Grades grades = new Grades()
            .aNumber(DEFAULT_A_NUMBER)
            .bNumber(DEFAULT_B_NUMBER)
            .cNumber(DEFAULT_C_NUMBER)
            .dNumber(DEFAULT_D_NUMBER)
            .fNumber(DEFAULT_F_NUMBER)
            .wNumber(DEFAULT_W_NUMBER)
            .studentNumber(DEFAULT_STUDENT_NUMBER);
        return grades;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grades createUpdatedEntity(EntityManager em) {
        Grades grades = new Grades()
            .aNumber(UPDATED_A_NUMBER)
            .bNumber(UPDATED_B_NUMBER)
            .cNumber(UPDATED_C_NUMBER)
            .dNumber(UPDATED_D_NUMBER)
            .fNumber(UPDATED_F_NUMBER)
            .wNumber(UPDATED_W_NUMBER)
            .studentNumber(UPDATED_STUDENT_NUMBER);
        return grades;
    }

    @BeforeEach
    public void initTest() {
        grades = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrades() throws Exception {
        int databaseSizeBeforeCreate = gradesRepository.findAll().size();

        // Create the Grades
        restGradesMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grades)))
            .andExpect(status().isCreated());

        // Validate the Grades in the database
        List<Grades> gradesList = gradesRepository.findAll();
        assertThat(gradesList).hasSize(databaseSizeBeforeCreate + 1);
        Grades testGrades = gradesList.get(gradesList.size() - 1);
        assertThat(testGrades.getaNumber()).isEqualTo(DEFAULT_A_NUMBER);
        assertThat(testGrades.getbNumber()).isEqualTo(DEFAULT_B_NUMBER);
        assertThat(testGrades.getcNumber()).isEqualTo(DEFAULT_C_NUMBER);
        assertThat(testGrades.getdNumber()).isEqualTo(DEFAULT_D_NUMBER);
        assertThat(testGrades.getfNumber()).isEqualTo(DEFAULT_F_NUMBER);
        assertThat(testGrades.getwNumber()).isEqualTo(DEFAULT_W_NUMBER);
        assertThat(testGrades.getStudentNumber()).isEqualTo(DEFAULT_STUDENT_NUMBER);
    }

    @Test
    @Transactional
    public void createGradesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradesRepository.findAll().size();

        // Create the Grades with an existing ID
        grades.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradesMockMvc.perform(post("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grades)))
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        List<Grades> gradesList = gradesRepository.findAll();
        assertThat(gradesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGrades() throws Exception {
        // Initialize the database
        gradesRepository.saveAndFlush(grades);

        // Get all the gradesList
        restGradesMockMvc.perform(get("/api/grades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grades.getId().intValue())))
            .andExpect(jsonPath("$.[*].aNumber").value(hasItem(DEFAULT_A_NUMBER)))
            .andExpect(jsonPath("$.[*].bNumber").value(hasItem(DEFAULT_B_NUMBER)))
            .andExpect(jsonPath("$.[*].cNumber").value(hasItem(DEFAULT_C_NUMBER)))
            .andExpect(jsonPath("$.[*].dNumber").value(hasItem(DEFAULT_D_NUMBER)))
            .andExpect(jsonPath("$.[*].fNumber").value(hasItem(DEFAULT_F_NUMBER)))
            .andExpect(jsonPath("$.[*].wNumber").value(hasItem(DEFAULT_W_NUMBER)))
            .andExpect(jsonPath("$.[*].studentNumber").value(hasItem(DEFAULT_STUDENT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getGrades() throws Exception {
        // Initialize the database
        gradesRepository.saveAndFlush(grades);

        // Get the grades
        restGradesMockMvc.perform(get("/api/grades/{id}", grades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grades.getId().intValue()))
            .andExpect(jsonPath("$.aNumber").value(DEFAULT_A_NUMBER))
            .andExpect(jsonPath("$.bNumber").value(DEFAULT_B_NUMBER))
            .andExpect(jsonPath("$.cNumber").value(DEFAULT_C_NUMBER))
            .andExpect(jsonPath("$.dNumber").value(DEFAULT_D_NUMBER))
            .andExpect(jsonPath("$.fNumber").value(DEFAULT_F_NUMBER))
            .andExpect(jsonPath("$.wNumber").value(DEFAULT_W_NUMBER))
            .andExpect(jsonPath("$.studentNumber").value(DEFAULT_STUDENT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingGrades() throws Exception {
        // Get the grades
        restGradesMockMvc.perform(get("/api/grades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrades() throws Exception {
        // Initialize the database
        gradesService.save(grades);

        int databaseSizeBeforeUpdate = gradesRepository.findAll().size();

        // Update the grades
        Grades updatedGrades = gradesRepository.findById(grades.getId()).get();
        // Disconnect from session so that the updates on updatedGrades are not directly saved in db
        em.detach(updatedGrades);
        updatedGrades
            .aNumber(UPDATED_A_NUMBER)
            .bNumber(UPDATED_B_NUMBER)
            .cNumber(UPDATED_C_NUMBER)
            .dNumber(UPDATED_D_NUMBER)
            .fNumber(UPDATED_F_NUMBER)
            .wNumber(UPDATED_W_NUMBER)
            .studentNumber(UPDATED_STUDENT_NUMBER);

        restGradesMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrades)))
            .andExpect(status().isOk());

        // Validate the Grades in the database
        List<Grades> gradesList = gradesRepository.findAll();
        assertThat(gradesList).hasSize(databaseSizeBeforeUpdate);
        Grades testGrades = gradesList.get(gradesList.size() - 1);
        assertThat(testGrades.getaNumber()).isEqualTo(UPDATED_A_NUMBER);
        assertThat(testGrades.getbNumber()).isEqualTo(UPDATED_B_NUMBER);
        assertThat(testGrades.getcNumber()).isEqualTo(UPDATED_C_NUMBER);
        assertThat(testGrades.getdNumber()).isEqualTo(UPDATED_D_NUMBER);
        assertThat(testGrades.getfNumber()).isEqualTo(UPDATED_F_NUMBER);
        assertThat(testGrades.getwNumber()).isEqualTo(UPDATED_W_NUMBER);
        assertThat(testGrades.getStudentNumber()).isEqualTo(UPDATED_STUDENT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingGrades() throws Exception {
        int databaseSizeBeforeUpdate = gradesRepository.findAll().size();

        // Create the Grades

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradesMockMvc.perform(put("/api/grades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grades)))
            .andExpect(status().isBadRequest());

        // Validate the Grades in the database
        List<Grades> gradesList = gradesRepository.findAll();
        assertThat(gradesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrades() throws Exception {
        // Initialize the database
        gradesService.save(grades);

        int databaseSizeBeforeDelete = gradesRepository.findAll().size();

        // Delete the grades
        restGradesMockMvc.perform(delete("/api/grades/{id}", grades.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grades> gradesList = gradesRepository.findAll();
        assertThat(gradesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grades.class);
        Grades grades1 = new Grades();
        grades1.setId(1L);
        Grades grades2 = new Grades();
        grades2.setId(grades1.getId());
        assertThat(grades1).isEqualTo(grades2);
        grades2.setId(2L);
        assertThat(grades1).isNotEqualTo(grades2);
        grades1.setId(null);
        assertThat(grades1).isNotEqualTo(grades2);
    }
}
