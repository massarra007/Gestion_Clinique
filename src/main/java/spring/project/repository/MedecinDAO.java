package spring.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.project.entities.Medecin;
import spring.project.entities.Specialite;

import java.util.List;

public interface MedecinDAO extends JpaRepository<Medecin, Long> {
    Page<Medecin> findByNomContains(String nom, Pageable pageable);

    Medecin findByNom(String medecinName);
    List<Medecin> findBySpecialite(Specialite specialite);

    @Query("SELECT COUNT(m) FROM Medecin m WHERE m.specialite = :specialite")
    long countBySpecialite(@Param("specialite") Specialite specialite);


}
