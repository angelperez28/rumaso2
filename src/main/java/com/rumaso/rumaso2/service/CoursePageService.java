package com.rumaso.rumaso2.service;

import com.rumaso.rumaso2.domain.CoursePage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CoursePage}.
 */
public interface CoursePageService {

    /**
     * Save a coursePage.
     *
     * @param coursePage the entity to save.
     * @return the persisted entity.
     */
    CoursePage save(CoursePage coursePage);

    /**
     * Get all the coursePages.
     *
     * @return the list of entities.
     */
    List<CoursePage> findAll();

    /**
     * Get all the coursePages with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CoursePage> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" coursePage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursePage> findOne(Long id);

    /**
     * Delete the "id" coursePage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
