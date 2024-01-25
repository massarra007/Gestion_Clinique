package spring.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.entities.Patient;

public interface PatientDAO extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContains(String nom, Pageable pageable);
    Patient findByNom(String patientName);

    long countByMalade(boolean malade);
}