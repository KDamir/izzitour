package kz.izzi.repository;

import kz.izzi.domain.Filter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Filter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {

}
