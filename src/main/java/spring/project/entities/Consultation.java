package spring.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of consultation must be in the past")
    @NotNull(message = "Date of consultation is required")
    private Date dateConsultation;

    @Size(max = 255, message = "Rapport length must be less than or equal to 255 characters")
    private String rapport;

    @OneToOne
    @NotNull(message = "RendezVous is required")
    private RendezVous rendezVous;

    @ManyToOne
    @NotNull(message = "DossierMedical is required")
    private DossierMedical dossierMedical;
}



