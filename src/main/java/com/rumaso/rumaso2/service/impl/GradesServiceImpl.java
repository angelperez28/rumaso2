package com.rumaso.rumaso2.service.impl;

import com.rumaso.rumaso2.service.GradesService;
import com.rumaso.rumaso2.domain.Grades;
import com.rumaso.rumaso2.repository.GradesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Grades}.
 */
@Service
@Transactional
public class GradesServiceImpl implements GradesService {

    private final Logger log = LoggerFactory.getLogger(GradesServiceImpl.class);

    private final GradesRepository gradesRepository;

    public GradesServiceImpl(GradesRepository gradesRepository) {
        this.gradesRepository = gradesRepository;
    }

    /**
     * Save a grades.
     *
     * @param grades the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Grades save(Grades grades) {
        log.debug("Request to save Grades : {}", grades);
        return gradesRepository.save(grades);
    }

    /**
     * Get all the grades.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Grades> findAll() {
        log.debug("Request to get all Grades");
        return gradesRepository.findAll();
    }


    /**
     * Get one grades by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Grades> findOne(Long id) {
        log.debug("Request to get Grades : {}", id);
        return gradesRepository.findById(id);
    }

    /**
     * Delete the grades by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grades : {}", id);
        gradesRepository.deleteById(id);
    }
}
