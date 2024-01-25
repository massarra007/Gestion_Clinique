package spring.project.service;

import spring.project.entities.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface ICliniqueService {

    // RendezVous saveRendezVous(String patientName, String medecinName, RendezVous rendezVous);
    // Consultation saveConsultation(Consultation consultation);

    // DossierMedical saveDossierMedical(String patientName, DossierMedical dossierMedical);

    void savePatient(Patient patient);

    Page<Patient> getAllPatients(int page, int size, String keyword);

    void deletePatient(Long id);

    Patient getPatientById(Long id);


    long countPatientsByMalade(boolean malade);


    Page<Medecin> getAllMedecins(int page, int size, String keyword);

    void deleteMedecin(Long id);

    Medecin getMedecinById(Long id);

    void saveMedecin(Medecin medecin);


    List<Medecin> getMedecinsBySpecialite(Specialite specialite);

    long countMedecinsBySpecialite(Specialite specialite);



    Page<RendezVous> getAllRendezVous(int page, int size, Date keyword);

    void deleteRendezVous(Long id);

    RendezVous getRendezVousById(Long id);

    void saveRendezVous(RendezVous rendezVous, String patientName, String medecinName);


    List<RendezVous> getRendezVousByStatus(StatusRDV statusRDV);

    long countRendezVousByStatus(StatusRDV statusRDV);

    Page<DossierMedical> getAllDossierMedical(int page, int size, String keyword);

    void deleteDossierMedical(Long id);

    DossierMedical getDossierMedicalById(Long id);

    void saveDossierMedical(String patientName, DossierMedical dossierMedical);

 Page<Consultation> getAllConsultations(int page, int size);

 void deleteConsultation(Long id);

 Consultation getConsultationById(Long id);

 void saveConsultation(Consultation consultation);

}
