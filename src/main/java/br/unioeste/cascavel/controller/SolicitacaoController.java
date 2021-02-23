package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.service.SolicitacoesService;

@Controller
public class SolicitacaoController {
    
    @Autowired
    private SolicitacoesService solicitacaoService;

    @GetMapping("/solicitacoes")
    public String viewHomePage(Model model){
        model.addAttribute("ListaSolicitacoes", solicitacaoService.getAllSolicitacoes());
        return "solicitacoes";
    }
    @GetMapping("/formIncluirSolicitacao")
    public String formIncluirSolicitacao(Model model){
        Solicitacao solicitacao = new Solicitacao();
        model.addAttribute("solicitacao", solicitacao);
        return "nova_solicitacao";
    }

    @PostMapping("/incluirSolicitacao")
    public String incluir(@ModelAttribute("solicitacao") Solicitacao solicitacao) {
        solicitacaoService.save(solicitacao);
        return "redirect:/solicitacoes";  
    }
    @GetMapping("/formAlterarSolicitacao/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Solicitacao solicitacao = solicitacaoService.getSolicitacaoById(id);
        model.addAttribute("solicitacao", solicitacao);
        return "alterar_solicitacao";  
    }

    @GetMapping("/apagarSolicitacao/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.solicitacaoService.apagarSolicitacaoById(id);
        return "redirect:/solicitacoes";  
    }

    @GetMapping("/statusSolicitacao/{id}")
    public String visualizeStatus(@PathVariable (value = "id") long id_solicitacao, Model model){
        model.addAttribute("ListaStatus", solicitacaoService.getAllStatus(id_solicitacao));
        return "status_solicitacao";
    }

    @GetMapping("/eventosSolicitacao/{id}")
    public String visualizeEventos(@PathVariable (value = "id") long id_solicitacao, Model model){
        model.addAttribute("ListaEventos", solicitacaoService.getAllEventos(id_solicitacao));
        return "eventos_solicitacao";
    }

    @GetMapping("/documentosSolicitacao/{id}")
    public String visualizeDocumentos(@PathVariable (value = "id") long id_solicitacao, Model model){
        model.addAttribute("ListaDocumentos", solicitacaoService.getAllDocumentos(id_solicitacao));
        return "documentos_solicitacao";
    }

}
