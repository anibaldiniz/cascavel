package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Perfil;
import br.unioeste.cascavel.service.PerfisService;

@Controller
public class PerfilController {
    
    @Autowired
    private PerfisService perfilService;

    @GetMapping("/perfis")
    public String viewHomePage(Model model){
        model.addAttribute("ListaPerfis", perfilService.getAllPerfis());
        return "perfis";
    }
    @GetMapping("/formIncluirPerfil")
    public String formIncluirPerfil(Model model){
        Perfil perfil = new Perfil();
        model.addAttribute("perfil", perfil);
        return "nova_perfil";
    }

    @PostMapping("/incluirPerfil")
    public String incluir(@ModelAttribute("perfil") Perfil perfil) {
        perfilService.save(perfil);
        return "redirect:/perfil";  
    }
    @GetMapping("/formAlterarPerfil/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Perfil perfil = perfilService.getPerfilById(id);
        model.addAttribute("perfil", perfil);
        return "alterar_perfil";  
    }

    @GetMapping("/apagarPerfil/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.perfilService.apagarPerfilById(id);
        return "redirect:/perfil";  
    }

    @GetMapping("/usuariosPerfil/{id}")
    public String visualizeUsuarios(@PathVariable (value = "id") long id_perfil, Model model){
        model.addAttribute("ListaUsuarios", perfilService.getAllUsuarios(id_perfil));
        // String nomePerfil = perfilService.getPerfilById(id_perfil).getNome();
        // model.addAttribute("nome_perfil", nomePerfil);
        return "usuarios_perfil";
    }

}
