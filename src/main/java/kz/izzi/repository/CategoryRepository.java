package kz.izzi.repository;

import kz.izzi.domain.Category;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
