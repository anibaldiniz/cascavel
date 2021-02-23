package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Email;
import br.unioeste.cascavel.service.EmailsService;

@Controller
public class EmailController {
    
    @Autowired
    private EmailsService emailService;

    @GetMapping("/emails")
    public String viewHomePage(Model model){
        model.addAttribute("ListaEmails", emailService.getAllEmails());
        return "emails";
    }
    @GetMapping("/formIncluirEmail")
    public String formIncluirEmail(Model model){
        Email email = new Email();
        model.addAttribute("email", email);
        return "nova_email";
    }

    @PostMapping("/incluirEmail")
    public String incluir(@ModelAttribute("email") Email email) {
        emailService.save(email);
        return "redirect:/email";  
    }
    @GetMapping("/formAlterarEmail/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Email email = emailService.getEmailById(id);
        model.addAttribute("email", email);
        return "alterar_email";  
    }

    @GetMapping("/apagarEmail/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.emailService.apagarEmailById(id);
        return "redirect:/email";  
    }
}
