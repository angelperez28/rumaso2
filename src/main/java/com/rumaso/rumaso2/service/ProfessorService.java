package com.rumaso.rumaso2.service;

import com.rumaso.rumaso2.domain.Professor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Professor}.
 */
public interface ProfessorService {

    /**
     * Save a professor.
     *
     * @param professor the entity to save.
     * @return the persisted entity.
     */
    Professor save(Professor professor);

    /**
     * Get all the professors.
     *
     * @return the list of entities.
     */
    List<Professor> findAll();
    /**
     * Get all the ProfessorDTO where Section is {@code null}.
     *
     * @return the list of entities.
     */
    List<Professor> findAllWhereSectionIsNull();


    /**
     * Get the "id" professor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Professor> findOne(Long id);

    /**
     * Delete the "id" professor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
