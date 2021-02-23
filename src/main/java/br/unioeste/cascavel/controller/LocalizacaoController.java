package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Localizacao;
import br.unioeste.cascavel.service.LocalizacoesService;

@Controller
public class LocalizacaoController {
    
    @Autowired
    private LocalizacoesService localizacaoService;

    @GetMapping("/localizacoes")
    public String viewHomePage(Model model){
        model.addAttribute("ListaLocalizacoes", localizacaoService.getAllLocalizacoes());
        return "localizacoes";
    }
    @GetMapping("/formIncluirLocalizacao")
    public String formIncluirLocalizacao(Model model){
        Localizacao localizacao = new Localizacao();
        model.addAttribute("localizacao", localizacao);
        return "nova_localizacao";
    }

    @PostMapping("/incluirLocalizacao")
    public String incluir(@ModelAttribute("localizacao") Localizacao localizacao) {
        localizacaoService.save(localizacao);
        return "redirect:/localizacoes";  
    }
    @GetMapping("/formAlterarLocalizacao/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Localizacao localizacao = localizacaoService.getLocalizacaoById(id);
        model.addAttribute("localizacao", localizacao);
        return "alterar_localizacao";  
    }

    @GetMapping("/apagarLocalizacao/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.localizacaoService.apagarLocalizacaoById(id);
        return "redirect:/localizacoes";  
    }

    @GetMapping("/usuariosNaLocalizacao/{id}")
    public String visualizeUsuariosLocal(@PathVariable (value = "id") long id_localizacao, Model model){
        model.addAttribute("ListaUsuarios", localizacaoService.getAllUsuarios(id_localizacao));
        return "usuarios_localizacao";
    }
    
    @GetMapping("/gruposNaLocalizacao/{id}")
    public String visualizeGruposLocal(@PathVariable (value = "id") long id_localizacao, Model model){
        model.addAttribute("ListaGrupos", localizacaoService.getAllGrupos(id_localizacao));
        // String nomeLocalizacao = localizacaoService.getLocalizacaoById(id_localizacao).getNome();
        // model.addAttribute("nome_localizacao", nomeLocalizacao);
        return "grupos_localizacao";
    }

    @GetMapping("/unidadesNaLocalizacao/{id}")
    public String visualizUnidadesLocal(@PathVariable (value = "id") long id_localizacao, Model model){
        model.addAttribute("ListaUnidades", localizacaoService.getAllUnidades(id_localizacao));
        // String nomeLocalizacao = localizacaoService.getLocalizacaoById(id_localizacao).getNome();
        // model.addAttribute("nome_localizacao", nomeLocalizacao);
        return "unidades_localizacao";
    }
}
