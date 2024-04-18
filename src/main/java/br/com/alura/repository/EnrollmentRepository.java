package br.com.alura.repository;

import br.com.alura.model.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
