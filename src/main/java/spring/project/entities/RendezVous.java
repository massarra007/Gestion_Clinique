package spring.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "StatusRDV is required")
    @Enumerated(EnumType.STRING)
    private StatusRDV statusRDV;

    @NotNull(message = "Patient is required")
    @ManyToOne
    private Patient patient;

    @NotNull(message = "Medecin is required")
    @ManyToOne
    private Medecin medecin;
}
