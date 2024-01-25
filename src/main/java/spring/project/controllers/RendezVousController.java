package spring.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.project.entities.RendezVous;
import spring.project.entities.StatusRDV;
import spring.project.repository.MedecinDAO;
import spring.project.repository.PatientDAO;
import spring.project.repository.RendezVousDAO;
import spring.project.service.ICliniqueService;
import spring.project.service.ICliniqueServiceImpl;

import java.util.Date;


//@RestController
@Controller
@AllArgsConstructor
public class RendezVousController {
    PatientDAO patientDAO;
    RendezVousDAO rendezVousDAO;
    MedecinDAO medecinDAO;

    ICliniqueService cliniqueService;
    ICliniqueServiceImpl rendezVousService;

    @GetMapping(path = "/user/rdv")
    public String rendezVous(Model model,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size,
                             @RequestParam(name = "keyword", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date keyword) {
        Page<RendezVous> pageRDV = rendezVousService.getAllRendezVous(page, size, keyword);
        model.addAttribute("listRDV", pageRDV.getContent());
        model.addAttribute("pages", new int[pageRDV.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "RDV/Rendezvous";
    }

    @GetMapping(path = "/admin/formRDV")
    public String formRendezVous(Model model, String nomPatient, String nomMedecin) {
        model.addAttribute("rendezvous", new RendezVous());
        model.addAttribute("nomPatient", nomPatient);
        model.addAttribute("nomMedecin", nomMedecin);
        model.addAttribute("patients", patientDAO.findAll());
        model.addAttribute("medecins", medecinDAO.findAll());
        return "RDV/formRDV";
    }

    @PostMapping("/admin/saveRDV")
    public String saveRendezVous(@ModelAttribute RendezVous rendezVous,
                                 @RequestParam("patientName") String patientName,
                                 @RequestParam("medecinName") String medecinName,
                                 @RequestParam("page") String pageStr) {
        int page = 0;

        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
        }

        rendezVousService.saveRendezVous(rendezVous, patientName, medecinName);

        return "redirect:/user/rdv?page=" + page;
    }

    @GetMapping(path = "/admin/deleteRDV")
    public String deleteRDV(@RequestParam Long id, @RequestParam int page) {
        rendezVousService.deleteRendezVous(id);
        return "redirect:/user/rdv?page=" + page;
    }

    @GetMapping(path = "/admin/EditRDV")
    public String editRDV(Model model, @RequestParam Long id, @RequestParam int page) {
        RendezVous rendezVous = rendezVousService.getRendezVousById(id);

        if (rendezVous == null) {
            throw new RuntimeException("Rendez-vous introuvable");
        }

        model.addAttribute("rendezVous", rendezVous);
        model.addAttribute("page", page);
        model.addAttribute("patients", patientDAO.findAll());
        model.addAttribute("medecins", medecinDAO.findAll());

        return "RDV/EditRDV";
    }


    @GetMapping("/admin/statistics")
    public String showRendezVousStatistics(Model model) {
        long pendingCount = cliniqueService.countRendezVousByStatus(StatusRDV.PENDING);
        long canceledCount = cliniqueService.countRendezVousByStatus(StatusRDV.CANCELED);
        long doneCount = cliniqueService.countRendezVousByStatus(StatusRDV.DONE);

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("canceledCount", canceledCount);
        model.addAttribute("doneCount", doneCount);

        return "RDV/statistics";
    }
}
