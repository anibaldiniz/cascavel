package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Telefone;
import br.unioeste.cascavel.service.TelefonesService;

@Controller
public class TelefoneController {
    
    @Autowired
    private TelefonesService telefoneService;

    @GetMapping("/telefones")
    public String viewHomePage(Model model){
        model.addAttribute("ListaTelefones", telefoneService.getAllTelefones());
        return "telefones";
    }
    @GetMapping("/formIncluirTelefone")
    public String formIncluirTelefone(Model model){
        Telefone telefone = new Telefone();
        model.addAttribute("telefone", telefone);
        return "nova_telefone";
    }

    @PostMapping("/incluirTelefone")
    public String incluir(@ModelAttribute("telefone") Telefone telefone) {
        telefoneService.save(telefone);
        return "redirect:/telefones";  
    }
    @GetMapping("/formAlterarTelefone/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Telefone telefone = telefoneService.getTelefoneById(id);
        model.addAttribute("telefone", telefone);
        return "alterar_telefone";  
    }

    @GetMapping("/apagarTelefone/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.telefoneService.apagarTelefoneById(id);
        return "redirect:/telefones";  
    }

    @GetMapping("/usuariosTelefone/{id}")
    public String visualizeUsuarios(@PathVariable (value = "id") long id_telefone, Model model){
        model.addAttribute("ListaUsuarios", telefoneService.getAllUsuarios(id_telefone));
        // String nomeTelefone = telefoneService.getTelefoneById(id_telefone).getNome();
        // model.addAttribute("nome_telefone", nomeTelefone);
        return "usuarios_telefone";
    }

}
