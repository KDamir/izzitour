package kz.izzi.repository;

import kz.izzi.domain.Answer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("select distinct answer from Answer answer left join fetch answer.nextQuestions")
    List<Answer> findAllWithEagerRelationships();

    @Query("select answer from Answer answer left join fetch answer.nextQuestions where answer.id =:id")
    Answer findOneWithEagerRelationships(@Param("id") Long id);

}
