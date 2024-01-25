package spring.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spring.project.entities.*;
import spring.project.repository.*;

import java.util.Date;


@SpringBootApplication
public class GestionClinique implements CommandLineRunner {

	public static void main(String[] args) {
	ApplicationContext ctx = SpringApplication.run(GestionClinique.class, args);


	}

	private final PatientDAO patientRepository;
	private final MedecinDAO medecinRepository;
	private final RendezVousDAO rendezVousRepository;
	private final DossierMedicalDAO dossierMedicalRepository;
	private final ConsultationDAO consultationRepository;

	@Autowired
	public GestionClinique(PatientDAO patientRepository, MedecinDAO medecinRepository,
					  RendezVousDAO rendezVousRepository, DossierMedicalDAO dossierMedicalRepository,
					  ConsultationDAO consultationRepository) {
		this.patientRepository = patientRepository;
		this.medecinRepository = medecinRepository;
		this.rendezVousRepository = rendezVousRepository;
		this.dossierMedicalRepository = dossierMedicalRepository;
		this.consultationRepository = consultationRepository;
	}
	@Override
	public void run(String... args) throws Exception {
		Patient patient1 = new Patient(null, "John", "Doe", new Date(), true, "Address1", "123456789", Genre.femme);
		Patient patient2 = new Patient(null, "Jane", "Smith", new Date(), false, "Address2", "987654321", Genre.homme);
		Patient patient3 = new Patient(null, "ahmed", "Doe", new Date(), true, "Address1", "123456789", Genre.femme);
		Patient patient4 = new Patient(null, "sami", "Smith", new Date(), false, "Address2", "987654321", Genre.homme);
		Patient patient5 = new Patient(null, "mohsen", "Doe", new Date(), true, "Address1", "123456789", Genre.femme);
		Patient patient6 = new Patient(null, "ali", "Smith", new Date(), false, "Address2", "987654321", Genre.homme);

		Medecin medecin1 = new Medecin(null, "Dr. Smith", "Jane", "drsmith@example.com", Specialite.cardiologie, 20);
		Medecin medecin2 = new Medecin(null, "Dr. Johnson", "Alex", "drjohnson@example.com", Specialite.dermatologie, 15);

		RendezVous rendezVous1 = new RendezVous(null, new Date(), StatusRDV.PENDING, patient1, medecin1);
		RendezVous rendezVous2 = new RendezVous(null, new Date(), StatusRDV.DONE, patient2, medecin2);
		RendezVous rendezVous3 = new RendezVous(null, new Date(), StatusRDV.DONE, patient2, medecin2);

		DossierMedical dossierMedical1 = new DossierMedical(0, patient1, null);
		DossierMedical dossierMedical2 = new DossierMedical(0, patient2, null);

		Consultation consultation1 = new Consultation(null, new Date(), "Sample Report 1", rendezVous1, dossierMedical1);
		Consultation consultation2 = new Consultation(null, new Date(), "Sample Report 2", rendezVous2, dossierMedical2);
		Consultation consultation3 = new Consultation(null, new Date(), "Sample Report 2", rendezVous3, dossierMedical1);

		patientRepository.save(patient1);
		patientRepository.save(patient2);
		patientRepository.save(patient3);
		patientRepository.save(patient4);
		patientRepository.save(patient5);
		patientRepository.save(patient6);


		medecinRepository.save(medecin1);
		medecinRepository.save(medecin2);

		rendezVousRepository.save(rendezVous1);
		rendezVousRepository.save(rendezVous2);
		rendezVousRepository.save(rendezVous3);


		dossierMedicalRepository.save(dossierMedical1);
		dossierMedicalRepository.save(dossierMedical2);

		consultationRepository.save(consultation1);
		consultationRepository.save(consultation2);
		consultationRepository.save(consultation3);
	}
	}
