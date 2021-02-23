package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Unidade;
import br.unioeste.cascavel.service.UnidadesService;

@Controller
public class UnidadeController {
    
    @Autowired
    private UnidadesService unidadeService;

    @GetMapping("/unidades")
    public String viewHomePage(Model model){
        model.addAttribute("ListaUnidades", unidadeService.getAllUnidades());
        return "unidades";
    }
    @GetMapping("/formIncluirUnidade")
    public String formIncluirUnidade(Model model){
        Unidade unidade = new Unidade();
        model.addAttribute("unidade", unidade);
        return "nova_unidade";
    }

    @PostMapping("/incluirUnidade")
    public String incluir(@ModelAttribute("unidade") Unidade unidade) {
        unidadeService.save(unidade);
        return "redirect:/unidades";  
    }
    @GetMapping("/formAlterarUnidade/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Unidade unidade = unidadeService.getUnidadeById(id);
        model.addAttribute("unidade", unidade);
        return "alterar_unidade";  
    }

    @GetMapping("/apagarUnidade/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.unidadeService.apagarUnidadeById(id);
        return "redirect:/unidades";  
    }

    @GetMapping("/localizacoesUnidade/{id}")
    public String visualizeLocalizacoes(@PathVariable (value = "id") long id_unidade, Model model){
        model.addAttribute("ListaLocalizacoes", unidadeService.getAllLocalizacoes(id_unidade));
        return "localizacoes_unidade";
    }
    
    @GetMapping("/gruposUnidade/{id}")
    public String visualizeGrupos(@PathVariable (value = "id") long id_unidade, Model model){
        model.addAttribute("ListaGrupos", unidadeService.getAllGrupos(id_unidade));
        return "grupos_unidade";
    }
}
