package kz.izzi.repository;

import kz.izzi.domain.Criterion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Criterion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriterionRepository extends JpaRepository<Criterion, Long> {

}
