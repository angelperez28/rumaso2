package com.rumaso.rumaso2.service;

import com.rumaso.rumaso2.domain.Section;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Section}.
 */
public interface SectionService {

    /**
     * Save a section.
     *
     * @param section the entity to save.
     * @return the persisted entity.
     */
    Section save(Section section);

    /**
     * Get all the sections.
     *
     * @return the list of entities.
     */
    List<Section> findAll();
    /**
     * Get all the SectionDTO where Grades is {@code null}.
     *
     * @return the list of entities.
     */
    List<Section> findAllWhereGradesIsNull();


    /**
     * Get the "id" section.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Section> findOne(Long id);

    /**
     * Delete the "id" section.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
