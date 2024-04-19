package br.com.alura.repository;

import br.com.alura.model.entity.CourseEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEvaluationRepository extends JpaRepository<CourseEvaluation, Long> {

    List<CourseEvaluation> findCourseEvaluationByCourseId(Long id);
}
