package br.unioeste.cascavel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Email;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.service.EmailsService;
import br.unioeste.cascavel.service.UsuariosService;

@Controller
public class EmailController {
    
    @Autowired
    private EmailsService emailService;

    @Autowired
    private UsuariosService usuarioService;

    @GetMapping("/emails")
    public String viewHomePage(Model model){
        model.addAttribute("ListaEmails", emailService.getAllEmails());
        return "emails";
    }

    @GetMapping("/formIncluirEmail")
    public String formIncluirEmail(Model model){
        Email email = new Email();
        model.addAttribute("email", email);
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        model.addAttribute("todosUsuarios", usuarios);
        return "novo_email";
    }

    @GetMapping("/formIncluirEmail/{id_usuario}")
    public String formIncluirEmailUsuario(@PathVariable (value = "id_usuario") long id, Model model){
        Email email = new Email();
        Usuario usuario = usuarioService.getUsuarioById(id);
        email.setUsuarioEmails(usuario);
        model.addAttribute("email_", email);
        model.addAttribute("usuario", usuario);
        return "novo_email";
    }


    @PostMapping("/incluirEmail")
    public String incluir(@ModelAttribute("email") Email email) {
        email.setUsuarioEmails(usuarioService.getUsuarioById(email.getUsuarioEmails().getId()));
        emailService.save(email);
        return "redirect:/usuarios";  
    }
    @GetMapping("/formAlterarEmail/{id}")
    public String alterar(@PathVariable (value = "id") String id, Model model) {
        Email email = emailService.getEmailById(id);
        model.addAttribute("email", email);
        return "alterar_email";  
    }

    @GetMapping("/apagarEmail/{id}")
    public String apagar(@PathVariable (value = "id") String id, Model model) {
        this.emailService.apagarEmailById(id);
        return "redirect:/usuarios";  
    }
}
