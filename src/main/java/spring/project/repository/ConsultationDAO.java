package spring.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.entities.Consultation;

public interface ConsultationDAO extends JpaRepository<Consultation, Long> {
}

