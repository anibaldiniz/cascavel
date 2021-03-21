package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.service.GruposService;

@Controller
public class GrupoController {
    
    @Autowired
    private GruposService grupoService;

    @GetMapping("/grupos")
    public String viewHomePage(Model model){
        model.addAttribute("ListaGrupos", grupoService.getAllGrupos());
        return "grupos";
    }
    @GetMapping("/formIncluirGrupo")
    public String formIncluirCategoria(Model model){
        Grupo grupo = new Grupo();
        model.addAttribute("grupo", grupo);
        return "novo_grupo";
    }

    @PostMapping("/incluirGrupo")
    public String incluir(@ModelAttribute("grupo") Grupo grupo) {
        grupoService.save(grupo);
        return "redirect:/grupos";  
    }
    @GetMapping("/formAlterarGrupo/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Grupo grupo = grupoService.getGrupoById(id);
        model.addAttribute("grupo", grupo);
        return "alterar_grupo";  
    }

    @GetMapping("/apagarGrupo/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.grupoService.apagarGrupoById(id);
        return "redirect:/grupos";  
    }

    @GetMapping("/usuariosGrupo/{id}")
    public String visualizeUsuarios(@PathVariable (value = "id") long id_grupo, Model model){
        model.addAttribute("ListaUsuarios", grupoService.getAllUsuarios(id_grupo));
        model.addAttribute("nome_grupo", grupoService.getGrupoById(id_grupo).getNome());
        return "usuarios_grupo";
    }
    
    @GetMapping("/usuariosResponsaveisGrupo/{id}")
    public String visualizeResponsaveisGrupo(@PathVariable (value = "id") long id_grupo, Model model){
        model.addAttribute("ListaResponsaveis", grupoService.getAllUsuarios(id_grupo));
        return "usuarios_grupo";
    }
}
