package com.rumaso.rumaso2.service.impl;

import com.rumaso.rumaso2.service.SectionService;
import com.rumaso.rumaso2.domain.Section;
import com.rumaso.rumaso2.repository.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Section}.
 */
@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final Logger log = LoggerFactory.getLogger(SectionServiceImpl.class);

    private final SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    /**
     * Save a section.
     *
     * @param section the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Section save(Section section) {
        log.debug("Request to save Section : {}", section);
        return sectionRepository.save(section);
    }

    /**
     * Get all the sections.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Section> findAll() {
        log.debug("Request to get all Sections");
        return sectionRepository.findAll();
    }



    /**
    *  Get all the sections where Grades is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Section> findAllWhereGradesIsNull() {
        log.debug("Request to get all sections where Grades is null");
        return StreamSupport
            .stream(sectionRepository.findAll().spliterator(), false)
            .filter(section -> section.getGrades() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one section by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Section> findOne(Long id) {
        log.debug("Request to get Section : {}", id);
        return sectionRepository.findById(id);
    }

    /**
     * Delete the section by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Section : {}", id);
        sectionRepository.deleteById(id);
    }
}
