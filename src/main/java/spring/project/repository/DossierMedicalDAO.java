package spring.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.entities.DossierMedical;

public interface DossierMedicalDAO extends JpaRepository<DossierMedical, Long> {
    Page<DossierMedical> findByPatientContains(String nom, Pageable pageable);
}
