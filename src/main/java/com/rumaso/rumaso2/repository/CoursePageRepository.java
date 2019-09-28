package com.rumaso.rumaso2.repository;
import com.rumaso.rumaso2.domain.CoursePage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CoursePage entity.
 */
@Repository
public interface CoursePageRepository extends JpaRepository<CoursePage, Long> {

    @Query(value = "select distinct coursePage from CoursePage coursePage left join fetch coursePage.sections",
        countQuery = "select count(distinct coursePage) from CoursePage coursePage")
    Page<CoursePage> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct coursePage from CoursePage coursePage left join fetch coursePage.sections")
    List<CoursePage> findAllWithEagerRelationships();

    @Query("select coursePage from CoursePage coursePage left join fetch coursePage.sections where coursePage.id =:id")
    Optional<CoursePage> findOneWithEagerRelationships(@Param("id") Long id);

}
