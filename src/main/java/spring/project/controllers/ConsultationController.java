package spring.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.entities.Consultation;
import spring.project.entities.DossierMedical;
import spring.project.entities.RendezVous;
import spring.project.service.ICliniqueServiceImpl;


//@RestController
@Controller
@AllArgsConstructor

public class ConsultationController {

     ICliniqueServiceImpl consultationService;

    @GetMapping("/user/consultations")
    public String consultations(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Consultation> pageConsultations = consultationService.getAllConsultations(page, size);
        model.addAttribute("listConsultations", pageConsultations.getContent());
        model.addAttribute("pages", new int[pageConsultations.getTotalPages()]);
        model.addAttribute("currentPage", page);

        return "consultation/consultations";
    }


    @GetMapping("/admin/formConsultation")
    public String formConsultation(Model model) {
        model.addAttribute("consultation", new Consultation());
        model.addAttribute("rendezVousList", consultationService.getAllRendezVous(0, 5, null));
        model.addAttribute("dossierMedicalList", consultationService.getAllDossierMedical(0, 5, ""));

        return "consultation/formConsultation";
    }

    @PostMapping("/admin/saveConsultation")
    public String saveConsultation(@ModelAttribute Consultation consultation,
                                   @RequestParam("rendezVousId") Long rendezVousId,
                                   @RequestParam("dossierMedicalId") Long dossierMedicalId) {

        RendezVous rendezVous = consultationService.getRendezVousById(rendezVousId);
        DossierMedical dossierMedical = consultationService.getDossierMedicalById(dossierMedicalId);

        if (rendezVous != null && dossierMedical != null) {
            consultation.setRendezVous(rendezVous);
            consultation.setDossierMedical(dossierMedical);

            consultationService.saveConsultation(consultation);

            return "redirect:/user/consultations";
        } else {

            return "redirect:/admin/formConsultation?error";
        }
    }

@GetMapping("/admin/deleteConsultation")
    public String deleteConsultation(@RequestParam Long id, @RequestParam int page) {
        consultationService.deleteConsultation(id);
        return "redirect:/user/consultations?page=" + page;
    }

    @GetMapping ("/admin/EditConsultation")
    public String editConsultation(Model model, @RequestParam Long id, @RequestParam int page) {
        Consultation consultation = consultationService.getConsultationById(id);

        if (consultation == null) {
            throw new RuntimeException("Consultation introuvable");
        }


        model.addAttribute("consultation", consultation);
        model.addAttribute("rendezVousList", consultationService.getAllRendezVous(0, 5, null));
        model.addAttribute("dossierMedicalList",  consultationService.getAllDossierMedical(0, 5, ""));
        model.addAttribute("page", page);


        return "consultation/editConsultation";
    }

}