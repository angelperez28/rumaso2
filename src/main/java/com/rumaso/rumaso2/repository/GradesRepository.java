package com.rumaso.rumaso2.repository;
import com.rumaso.rumaso2.domain.Grades;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Grades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradesRepository extends JpaRepository<Grades, Long> {

}
