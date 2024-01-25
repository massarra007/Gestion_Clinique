package spring.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.entities.DossierMedical;
import spring.project.repository.DossierMedicalDAO;
import spring.project.repository.PatientDAO;
import spring.project.service.ICliniqueService;
import spring.project.service.ICliniqueServiceImpl;


//@RestController
@Controller
@AllArgsConstructor
public class DossierMedicalController {
    PatientDAO patientDAO;
    DossierMedicalDAO dossierMedicalDAO;

    ICliniqueService cliniqueService;
    ICliniqueServiceImpl dossierMedicalService;

    @GetMapping(path = "user/dossierMedical")
    public String dossierMedical(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<DossierMedical> pageDossier = dossierMedicalService.getAllDossierMedical(page, size, keyword);
        model.addAttribute("listDossier", pageDossier.getContent());
        model.addAttribute("pages", new int[pageDossier.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "dossierMedical/listdossierMedical";
    }

    @GetMapping(path = "/admin/formDossierMedical")
    public String formDossierMedical(Model model, String nomPatient) {
        model.addAttribute("dossierMedical", new DossierMedical());
        model.addAttribute("nomPatient", nomPatient);
        model.addAttribute("patients", patientDAO.findAll());

        return "dossierMedical/formDossierMedical";
    }

    @PostMapping("/admin/saveDossierMedical")
    public String saveDossierMedical(@ModelAttribute DossierMedical dossierMedical,
                                     @RequestParam("patientName") String patientName,
                                     @RequestParam("page") String pageStr) {
        int page = 0;

        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
        }

        dossierMedicalService.saveDossierMedical(patientName, dossierMedical);

        return "redirect:/user/dossierMedical?page=" + page;
    }

    @GetMapping(path = "/admin/deleteDossierMedical")
    public String deleteDossierMedical(@RequestParam Long id, @RequestParam int page) {
        dossierMedicalService.deleteDossierMedical(id);
        return "redirect:/user/dossierMedical?page=" + page;
    }

    @GetMapping(path = "/admin/EditDossierMedical")
    public String editDossierMedical(Model model, @RequestParam Long id, @RequestParam int page) {
        DossierMedical dossierMedical = dossierMedicalService.getDossierMedicalById(id);

        if (dossierMedical == null) {
            throw new RuntimeException("Dossier Medical introuvable");
        }

        model.addAttribute("dossierMedical", dossierMedical);
        model.addAttribute("page", page);
        model.addAttribute("patients", patientDAO.findAll());

        return "dossierMedical/EditDossierMedical";
    }
    @GetMapping("/user/dossierMedicalDetails")
    public String dossierMedicalDetails(Model model, @RequestParam Long id) {
        DossierMedical dossierMedical = dossierMedicalService.getDossierMedicalById(id);

        if (dossierMedical == null) {
            throw new RuntimeException("Dossier Medical introuvable");
        }

        model.addAttribute("dossierMedical", dossierMedical);

        return "dossierMedical/detailsDossierMedical";
    }

}
