package com.rumaso.rumaso2.service;

import com.rumaso.rumaso2.domain.Grades;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Grades}.
 */
public interface GradesService {

    /**
     * Save a grades.
     *
     * @param grades the entity to save.
     * @return the persisted entity.
     */
    Grades save(Grades grades);

    /**
     * Get all the grades.
     *
     * @return the list of entities.
     */
    List<Grades> findAll();


    /**
     * Get the "id" grades.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Grades> findOne(Long id);

    /**
     * Delete the "id" grades.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
