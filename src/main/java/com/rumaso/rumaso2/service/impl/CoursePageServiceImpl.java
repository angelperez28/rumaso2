package com.rumaso.rumaso2.service.impl;

import com.rumaso.rumaso2.service.CoursePageService;
import com.rumaso.rumaso2.domain.CoursePage;
import com.rumaso.rumaso2.repository.CoursePageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CoursePage}.
 */
@Service
@Transactional
public class CoursePageServiceImpl implements CoursePageService {

    private final Logger log = LoggerFactory.getLogger(CoursePageServiceImpl.class);

    private final CoursePageRepository coursePageRepository;

    public CoursePageServiceImpl(CoursePageRepository coursePageRepository) {
        this.coursePageRepository = coursePageRepository;
    }

    /**
     * Save a coursePage.
     *
     * @param coursePage the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CoursePage save(CoursePage coursePage) {
        log.debug("Request to save CoursePage : {}", coursePage);
        return coursePageRepository.save(coursePage);
    }

    /**
     * Get all the coursePages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoursePage> findAll() {
        log.debug("Request to get all CoursePages");
        return coursePageRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the coursePages with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CoursePage> findAllWithEagerRelationships(Pageable pageable) {
        return coursePageRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one coursePage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CoursePage> findOne(Long id) {
        log.debug("Request to get CoursePage : {}", id);
        return coursePageRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the coursePage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoursePage : {}", id);
        coursePageRepository.deleteById(id);
    }
}
