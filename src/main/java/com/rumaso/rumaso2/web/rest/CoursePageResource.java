package com.rumaso.rumaso2.web.rest;

import com.rumaso.rumaso2.domain.CoursePage;
import com.rumaso.rumaso2.service.CoursePageService;
import com.rumaso.rumaso2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rumaso.rumaso2.domain.CoursePage}.
 */
@RestController
@RequestMapping("/api")
public class CoursePageResource {

    private final Logger log = LoggerFactory.getLogger(CoursePageResource.class);

    private static final String ENTITY_NAME = "coursePage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursePageService coursePageService;

    public CoursePageResource(CoursePageService coursePageService) {
        this.coursePageService = coursePageService;
    }

    /**
     * {@code POST  /course-pages} : Create a new coursePage.
     *
     * @param coursePage the coursePage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursePage, or with status {@code 400 (Bad Request)} if the coursePage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-pages")
    public ResponseEntity<CoursePage> createCoursePage(@RequestBody CoursePage coursePage) throws URISyntaxException {
        log.debug("REST request to save CoursePage : {}", coursePage);
        if (coursePage.getId() != null) {
            throw new BadRequestAlertException("A new coursePage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursePage result = coursePageService.save(coursePage);
        return ResponseEntity.created(new URI("/api/course-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-pages} : Updates an existing coursePage.
     *
     * @param coursePage the coursePage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursePage,
     * or with status {@code 400 (Bad Request)} if the coursePage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursePage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-pages")
    public ResponseEntity<CoursePage> updateCoursePage(@RequestBody CoursePage coursePage) throws URISyntaxException {
        log.debug("REST request to update CoursePage : {}", coursePage);
        if (coursePage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoursePage result = coursePageService.save(coursePage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, coursePage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-pages} : get all the coursePages.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coursePages in body.
     */
    @GetMapping("/course-pages")
    public List<CoursePage> getAllCoursePages(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all CoursePages");
        return coursePageService.findAll();
    }

    /**
     * {@code GET  /course-pages/:id} : get the "id" coursePage.
     *
     * @param id the id of the coursePage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursePage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-pages/{id}")
    public ResponseEntity<CoursePage> getCoursePage(@PathVariable Long id) {
        log.debug("REST request to get CoursePage : {}", id);
        Optional<CoursePage> coursePage = coursePageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursePage);
    }

    /**
     * {@code DELETE  /course-pages/:id} : delete the "id" coursePage.
     *
     * @param id the id of the coursePage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-pages/{id}")
    public ResponseEntity<Void> deleteCoursePage(@PathVariable Long id) {
        log.debug("REST request to delete CoursePage : {}", id);
        coursePageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
