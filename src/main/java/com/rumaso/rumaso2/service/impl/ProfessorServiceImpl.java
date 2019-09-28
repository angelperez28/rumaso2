package com.rumaso.rumaso2.service.impl;

import com.rumaso.rumaso2.service.ProfessorService;
import com.rumaso.rumaso2.domain.Professor;
import com.rumaso.rumaso2.repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Professor}.
 */
@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private final Logger log = LoggerFactory.getLogger(ProfessorServiceImpl.class);

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /**
     * Save a professor.
     *
     * @param professor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Professor save(Professor professor) {
        log.debug("Request to save Professor : {}", professor);
        return professorRepository.save(professor);
    }

    /**
     * Get all the professors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Professor> findAll() {
        log.debug("Request to get all Professors");
        return professorRepository.findAll();
    }



    /**
    *  Get all the professors where Section is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Professor> findAllWhereSectionIsNull() {
        log.debug("Request to get all professors where Section is null");
        return StreamSupport
            .stream(professorRepository.findAll().spliterator(), false)
            .filter(professor -> professor.getSection() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one professor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Professor> findOne(Long id) {
        log.debug("Request to get Professor : {}", id);
        return professorRepository.findById(id);
    }

    /**
     * Delete the professor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professor : {}", id);
        professorRepository.deleteById(id);
    }
}
