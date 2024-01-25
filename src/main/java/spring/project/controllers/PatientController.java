package spring.project.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.project.entities.Patient;
import spring.project.repository.PatientDAO;
import spring.project.service.ICliniqueServiceImpl;

import java.util.List;

//@RestController
@Controller
@AllArgsConstructor
public class PatientController {
     PatientDAO patientDAO;

     ICliniqueServiceImpl patientService;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagePatients = patientService.getAllPatients(page, size, keyword);
        model.addAttribute("listpatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patient/patients";
    }


    @GetMapping(path = "/user/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return  patientDAO.findAll();
    }



    @GetMapping(path = "/admin/delete")
    public String deletePatient(@RequestParam Long id,
                                @RequestParam String keyword,
                                @RequestParam int page) {
        patientService.deletePatient(id);
        return "redirect:/user/index" ;
    }



    @GetMapping(path="/")
    public String home(){
        return "redirect:/user/index";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";

    }

    @GetMapping("/403")
    public String accessPage() {
        return "403";
    }



    @GetMapping(path = "/user/listPatient")
    public String listPatient(Model model,
                              @RequestParam Long id,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "0") int page) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "patient/listPatient";
    }

    @GetMapping(path = "/admin/ShowPatient")
    public String showPatient(Model model,
                              @RequestParam Long id,
                              @RequestParam String keyword,
                              @RequestParam int page) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            throw new RuntimeException("Patient introuvable");
        }
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "patient/detailsPatient";
    }

    @GetMapping(path = "/admin/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/formPatients";
    }

    @PostMapping(path = "/admin/save")
    public String savePatient(Model model,
                              @Validated Patient patient,
                              BindingResult bindingResult,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) {
            return "patient/formPatients";
        }
        patientService.savePatient(patient);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/admin/EditPatient")
    public String editPatient(Model model,
                              @RequestParam Long id,
                              @RequestParam String keyword,
                              @RequestParam int page) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            throw new RuntimeException("Patient introuvable");
        }
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "patient/EditPatient";
    }

    @GetMapping("/user/statistics")
    public String showPatientStatistics(Model model) {
        long maladeCount = patientService.countPatientsByMalade(true);
        long nonMaladeCount = patientService.countPatientsByMalade(false);

        model.addAttribute("maladeCount", maladeCount);
        model.addAttribute("nonMaladeCount", nonMaladeCount);

        return "patient/statistics";
    }

}