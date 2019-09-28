package com.rumaso.rumaso2.web.rest;

import com.rumaso.rumaso2.domain.Grades;
import com.rumaso.rumaso2.service.GradesService;
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
 * REST controller for managing {@link com.rumaso.rumaso2.domain.Grades}.
 */
@RestController
@RequestMapping("/api")
public class GradesResource {

    private final Logger log = LoggerFactory.getLogger(GradesResource.class);

    private static final String ENTITY_NAME = "grades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradesService gradesService;

    public GradesResource(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    /**
     * {@code POST  /grades} : Create a new grades.
     *
     * @param grades the grades to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grades, or with status {@code 400 (Bad Request)} if the grades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grades")
    public ResponseEntity<Grades> createGrades(@RequestBody Grades grades) throws URISyntaxException {
        log.debug("REST request to save Grades : {}", grades);
        if (grades.getId() != null) {
            throw new BadRequestAlertException("A new grades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grades result = gradesService.save(grades);
        return ResponseEntity.created(new URI("/api/grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grades} : Updates an existing grades.
     *
     * @param grades the grades to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grades,
     * or with status {@code 400 (Bad Request)} if the grades is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grades couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grades")
    public ResponseEntity<Grades> updateGrades(@RequestBody Grades grades) throws URISyntaxException {
        log.debug("REST request to update Grades : {}", grades);
        if (grades.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Grades result = gradesService.save(grades);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grades.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grades} : get all the grades.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grades in body.
     */
    @GetMapping("/grades")
    public List<Grades> getAllGrades() {
        log.debug("REST request to get all Grades");
        return gradesService.findAll();
    }

    /**
     * {@code GET  /grades/:id} : get the "id" grades.
     *
     * @param id the id of the grades to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grades, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grades/{id}")
    public ResponseEntity<Grades> getGrades(@PathVariable Long id) {
        log.debug("REST request to get Grades : {}", id);
        Optional<Grades> grades = gradesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grades);
    }

    /**
     * {@code DELETE  /grades/:id} : delete the "id" grades.
     *
     * @param id the id of the grades to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grades/{id}")
    public ResponseEntity<Void> deleteGrades(@PathVariable Long id) {
        log.debug("REST request to delete Grades : {}", id);
        gradesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
