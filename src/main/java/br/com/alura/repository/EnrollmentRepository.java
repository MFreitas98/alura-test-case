package br.com.alura.repository;

import br.com.alura.model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e.course.id FROM Enrollment e GROUP BY e.course.id HAVING COUNT(e.course.id) > 4")
    List<Long> findCourseIdsWithFourOrMoreEnrollments();

}
