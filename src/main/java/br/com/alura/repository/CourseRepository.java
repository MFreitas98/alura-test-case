package br.com.alura.repository;

import br.com.alura.model.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByStatus(Boolean status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Course c set c.status = :status, c.inactivatedAt = :inactivatedAt where c.code = :code")
    void updateCourse(boolean status, String code , OffsetDateTime inactivatedAt);

    Optional<Course> findByCode(String code);
}
