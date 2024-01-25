package spring.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.project.entities.RendezVous;
import spring.project.entities.StatusRDV;

import java.util.Date;
import java.util.List;

public interface RendezVousDAO extends JpaRepository<RendezVous, Long> {
    Page<RendezVous> findByDate(Date date, Pageable pageable);

    List<RendezVous> findByStatusRDV(StatusRDV statusRDV);

    @Query("SELECT COUNT(r) FROM RendezVous r WHERE r.statusRDV = :statusRDV")
    long countByStatusRDV(@Param("statusRDV") StatusRDV statusRDV);


}
