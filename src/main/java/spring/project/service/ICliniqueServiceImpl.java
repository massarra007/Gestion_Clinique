package spring.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.project.entities.*;
import spring.project.repository.*;

import java.util.Date;
import java.util.List;

@Service
public class ICliniqueServiceImpl implements ICliniqueService {
private ConsultationDAO consultationDAO;
private MedecinDAO medecinDAO;
private PatientDAO patientDAO;
private RendezVousDAO rendezVousDAO;
private DossierMedicalDAO dossierMedicalDAO;

public ICliniqueServiceImpl(ConsultationDAO consultationDAO, MedecinDAO medecinDAO, PatientDAO patientDAO, RendezVousDAO rendezVousDAO ,DossierMedicalDAO dossierMedicalDAO){
    this.consultationDAO=consultationDAO;
    this.patientDAO=patientDAO;
    this.medecinDAO=medecinDAO;
    this.rendezVousDAO=rendezVousDAO;
    this.dossierMedicalDAO=dossierMedicalDAO;
}



    @Override
    public Page<Patient> getAllPatients(int page, int size, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return patientDAO.findAll(PageRequest.of(page, size));
        } else {
            return patientDAO.findByNomContains(keyword, PageRequest.of(page, size));
        }
    }

    @Override
    public void deletePatient(Long id) {
        patientDAO.deleteById(id);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientDAO.findById(id).orElse(null);
    }

    @Override
    public void savePatient(Patient patient) {
        patientDAO.save(patient);

    }

    @Override
    public long countPatientsByMalade(boolean malade) {
        return patientDAO.countByMalade(malade);
    }


    @Override
    public Page<Medecin> getAllMedecins(int page, int size, String keyword) {
        return medecinDAO.findByNomContains(keyword, PageRequest.of(page, size));
    }

    @Override
    public void deleteMedecin(Long id) {
        medecinDAO.deleteById(id);
    }

    @Override
    public Medecin getMedecinById(Long id) {
        return medecinDAO.findById(id).orElse(null);
    }

    @Override
    public void saveMedecin(Medecin medecin) {
        medecinDAO.save(medecin);
    }


    @Override
    public List<Medecin> getMedecinsBySpecialite(Specialite specialite) {
        return medecinDAO.findBySpecialite(specialite);
    }

    @Override
    public long countMedecinsBySpecialite(Specialite specialite) {
        return medecinDAO.countBySpecialite(specialite);
    }

    @Override
    public Page<RendezVous> getAllRendezVous(int page, int size, Date keyword) {
        if (keyword == null) {
            return rendezVousDAO.findAll(PageRequest.of(page, size));
        } else {
            return rendezVousDAO.findByDate(keyword, PageRequest.of(page, size));
        }
    }

    @Override
    public void deleteRendezVous(Long id) {
        rendezVousDAO.deleteById(id);
    }

    @Override
    public RendezVous getRendezVousById(Long id) {
        return rendezVousDAO.findById(id).orElse(null);
    }

    @Override
    public void saveRendezVous(RendezVous rendezVous, String patientName, String medecinName) {
        Patient patient = patientDAO.findByNom(patientName);
        Medecin medecin = medecinDAO.findByNom(medecinName);

        if (patient != null && medecin != null) {
            rendezVous.setPatient(patient);
            rendezVous.setMedecin(medecin);
            rendezVousDAO.save(rendezVous);
        }
    }

    @Override
    public List<RendezVous> getRendezVousByStatus(StatusRDV statusRDV) {
        return rendezVousDAO.findByStatusRDV(statusRDV);
    }

    @Override
    public long countRendezVousByStatus(StatusRDV statusRDV) {
        return rendezVousDAO.countByStatusRDV(statusRDV);
    }


    @Override
    public Page<DossierMedical> getAllDossierMedical(int page, int size, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return dossierMedicalDAO.findAll(PageRequest.of(page, size));
        } else {

            return dossierMedicalDAO.findByPatientContains(keyword, PageRequest.of(page, size));
        }
    }

    @Override
    public void deleteDossierMedical(Long id) {
        dossierMedicalDAO.deleteById(id);
    }

    @Override
    public DossierMedical getDossierMedicalById(Long id) {
        return dossierMedicalDAO.findById(id).orElse(null);
    }

    @Override
    public void saveDossierMedical(String patientName, DossierMedical dossierMedical) {
        Patient patient = patientDAO.findByNom(patientName);

        if (patient != null) {
            dossierMedical.setPatient(patient);
            dossierMedicalDAO.save(dossierMedical);
        }
    }



    @Override
    public Page<Consultation> getAllConsultations(int page, int size) {
        return consultationDAO.findAll(PageRequest.of(page, size));
    }

    @Override
    public void deleteConsultation(Long id) {
        consultationDAO.deleteById(id);
    }

    @Override
    public Consultation getConsultationById(Long id) {
        return consultationDAO.findById(id).orElse(null);
    }

    @Override
    public void saveConsultation(Consultation consultation) {
        consultationDAO.save(consultation);
    }

}
