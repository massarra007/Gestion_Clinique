package spring.project.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.project.entities.Medecin;
import spring.project.entities.Specialite;
import spring.project.service.ICliniqueServiceImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


//@RestController
@Controller
@AllArgsConstructor
public class MedecinController {

    @Autowired
    private JavaMailSender javaMailSender;
     ICliniqueServiceImpl medecinService;


    @GetMapping(path = "/user/medecins")
    public String medecin(Model model,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "5") int size,
                          @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Medecin> pageMedecins = medecinService.getAllMedecins(page, size, keyword);
        model.addAttribute("listMedecins", pageMedecins);
        model.addAttribute("pages", new int[pageMedecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "medecin/medecins";
    }

    @GetMapping(path = "/admin/formMedecin")
    public String formMedecin(Model model) {
        model.addAttribute("medecin", new Medecin());
        return "medecin/formMedecin";
    }

    @GetMapping(path = "/admin/deleteMedecin")
    public String deleteMedecin(@RequestParam Long id, @RequestParam String keyword, @RequestParam int page) {
        medecinService.deleteMedecin(id);
        return "redirect:/user/medecins?page=" + page + "&keyword=" + keyword;
    }

    @PostMapping(path = "/admin/saveMedecin")
    public String saveMedecin(Model model,
                              @Validated Medecin medecin,
                              BindingResult bindingResult,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) {
            return "medecin/formMedecin";
        }

        // Sauvegarde du médecin
        medecinService.saveMedecin(medecin);

        // Envoi d'un e-mail de bienvenue
        sendWelcomeEmail(medecin.getEmail());

        return "redirect:/user/medecins?page=" + page + "&keyword=" + keyword;
    }

    private void sendWelcomeEmail(String recipientEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Bienvenue !");
        mailMessage.setText("Bienvenue sur notre plateforme E-Clinique, Dr. " + recipientEmail + " !");
        javaMailSender.send(mailMessage);
    }

    @GetMapping(path = "/admin/EditMedecin")
    public String editMedecin(Model model, @RequestParam Long id, @RequestParam String keyword, @RequestParam int page) {
        Medecin medecin = medecinService.getMedecinById(id);
        if (medecin == null) {
            throw new RuntimeException("Medecin introuvable");
        }
        model.addAttribute("medecin", medecin);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "medecin/editMedecin";
    }

    @GetMapping(path = "/admin/ShowMedecin")
    public String showMedecin(Model model, @RequestParam Long id, @RequestParam String keyword, @RequestParam int page) {
        Medecin medecin = medecinService.getMedecinById(id);
        if (medecin == null) {
            throw new RuntimeException("Medecin introuvable");
        }
        model.addAttribute("medecin", medecin);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "medecin/detailsMedecin";
    }

    @GetMapping("/admin/specialite")
    public String showMedecinStatistics(Model model) {
        long generalisteCount = medecinService.countMedecinsBySpecialite(Specialite.Generaliste);
        long anesthesiologieCount = medecinService.countMedecinsBySpecialite(Specialite.anesthesiologie);
        long cardiologieCount = medecinService.countMedecinsBySpecialite(Specialite.cardiologie);
        long dermatologieCount = medecinService.countMedecinsBySpecialite(Specialite.dermatologie);

        model.addAttribute("generalisteCount", generalisteCount);
        model.addAttribute("anesthesiologieCount", anesthesiologieCount);
        model.addAttribute("cardiologieCount", cardiologieCount);
        model.addAttribute("dermatologieCount", dermatologieCount);

        return "medecin/statistics";
    }
}