package com.rumaso.rumaso2.web.rest;

import com.rumaso.rumaso2.Rumaso2App;
import com.rumaso.rumaso2.domain.CoursePage;
import com.rumaso.rumaso2.repository.CoursePageRepository;
import com.rumaso.rumaso2.service.CoursePageService;
import com.rumaso.rumaso2.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.rumaso.rumaso2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoursePageResource} REST controller.
 */
@SpringBootTest(classes = Rumaso2App.class)
public class CoursePageResourceIT {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    @Autowired
    private CoursePageRepository coursePageRepository;

    @Mock
    private CoursePageRepository coursePageRepositoryMock;

    @Mock
    private CoursePageService coursePageServiceMock;

    @Autowired
    private CoursePageService coursePageService;

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

    private MockMvc restCoursePageMockMvc;

    private CoursePage coursePage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoursePageResource coursePageResource = new CoursePageResource(coursePageService);
        this.restCoursePageMockMvc = MockMvcBuilders.standaloneSetup(coursePageResource)
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
    public static CoursePage createEntity(EntityManager em) {
        CoursePage coursePage = new CoursePage()
            .rating(DEFAULT_RATING);
        return coursePage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoursePage createUpdatedEntity(EntityManager em) {
        CoursePage coursePage = new CoursePage()
            .rating(UPDATED_RATING);
        return coursePage;
    }

    @BeforeEach
    public void initTest() {
        coursePage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoursePage() throws Exception {
        int databaseSizeBeforeCreate = coursePageRepository.findAll().size();

        // Create the CoursePage
        restCoursePageMockMvc.perform(post("/api/course-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursePage)))
            .andExpect(status().isCreated());

        // Validate the CoursePage in the database
        List<CoursePage> coursePageList = coursePageRepository.findAll();
        assertThat(coursePageList).hasSize(databaseSizeBeforeCreate + 1);
        CoursePage testCoursePage = coursePageList.get(coursePageList.size() - 1);
        assertThat(testCoursePage.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createCoursePageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursePageRepository.findAll().size();

        // Create the CoursePage with an existing ID
        coursePage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursePageMockMvc.perform(post("/api/course-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursePage)))
            .andExpect(status().isBadRequest());

        // Validate the CoursePage in the database
        List<CoursePage> coursePageList = coursePageRepository.findAll();
        assertThat(coursePageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoursePages() throws Exception {
        // Initialize the database
        coursePageRepository.saveAndFlush(coursePage);

        // Get all the coursePageList
        restCoursePageMockMvc.perform(get("/api/course-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coursePage.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCoursePagesWithEagerRelationshipsIsEnabled() throws Exception {
        CoursePageResource coursePageResource = new CoursePageResource(coursePageServiceMock);
        when(coursePageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCoursePageMockMvc = MockMvcBuilders.standaloneSetup(coursePageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoursePageMockMvc.perform(get("/api/course-pages?eagerload=true"))
        .andExpect(status().isOk());

        verify(coursePageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCoursePagesWithEagerRelationshipsIsNotEnabled() throws Exception {
        CoursePageResource coursePageResource = new CoursePageResource(coursePageServiceMock);
            when(coursePageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCoursePageMockMvc = MockMvcBuilders.standaloneSetup(coursePageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoursePageMockMvc.perform(get("/api/course-pages?eagerload=true"))
        .andExpect(status().isOk());

            verify(coursePageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCoursePage() throws Exception {
        // Initialize the database
        coursePageRepository.saveAndFlush(coursePage);

        // Get the coursePage
        restCoursePageMockMvc.perform(get("/api/course-pages/{id}", coursePage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coursePage.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }

    @Test
    @Transactional
    public void getNonExistingCoursePage() throws Exception {
        // Get the coursePage
        restCoursePageMockMvc.perform(get("/api/course-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoursePage() throws Exception {
        // Initialize the database
        coursePageService.save(coursePage);

        int databaseSizeBeforeUpdate = coursePageRepository.findAll().size();

        // Update the coursePage
        CoursePage updatedCoursePage = coursePageRepository.findById(coursePage.getId()).get();
        // Disconnect from session so that the updates on updatedCoursePage are not directly saved in db
        em.detach(updatedCoursePage);
        updatedCoursePage
            .rating(UPDATED_RATING);

        restCoursePageMockMvc.perform(put("/api/course-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoursePage)))
            .andExpect(status().isOk());

        // Validate the CoursePage in the database
        List<CoursePage> coursePageList = coursePageRepository.findAll();
        assertThat(coursePageList).hasSize(databaseSizeBeforeUpdate);
        CoursePage testCoursePage = coursePageList.get(coursePageList.size() - 1);
        assertThat(testCoursePage.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingCoursePage() throws Exception {
        int databaseSizeBeforeUpdate = coursePageRepository.findAll().size();

        // Create the CoursePage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursePageMockMvc.perform(put("/api/course-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursePage)))
            .andExpect(status().isBadRequest());

        // Validate the CoursePage in the database
        List<CoursePage> coursePageList = coursePageRepository.findAll();
        assertThat(coursePageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoursePage() throws Exception {
        // Initialize the database
        coursePageService.save(coursePage);

        int databaseSizeBeforeDelete = coursePageRepository.findAll().size();

        // Delete the coursePage
        restCoursePageMockMvc.perform(delete("/api/course-pages/{id}", coursePage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoursePage> coursePageList = coursePageRepository.findAll();
        assertThat(coursePageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursePage.class);
        CoursePage coursePage1 = new CoursePage();
        coursePage1.setId(1L);
        CoursePage coursePage2 = new CoursePage();
        coursePage2.setId(coursePage1.getId());
        assertThat(coursePage1).isEqualTo(coursePage2);
        coursePage2.setId(2L);
        assertThat(coursePage1).isNotEqualTo(coursePage2);
        coursePage1.setId(null);
        assertThat(coursePage1).isNotEqualTo(coursePage2);
    }
}
