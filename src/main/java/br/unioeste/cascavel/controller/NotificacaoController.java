package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Notificacao;
import br.unioeste.cascavel.service.NotificacoesService;

@Controller
public class NotificacaoController {
    
    @Autowired
    private NotificacoesService notificacaoService;

    @GetMapping("/notificacoes")
    public String viewHomePage(Model model){
        model.addAttribute("ListaNotificacoes", notificacaoService.getAllNotificacoes());
        return "notificacoes";
    }
    @GetMapping("/formIncluirNotificacao")
    public String formIncluirNotificacao(Model model){
        Notificacao notificacao = new Notificacao();
        model.addAttribute("notificacao", notificacao);
        return "nova_notificacao";
    }

    @PostMapping("/incluirNotificacao")
    public String incluir(@ModelAttribute("notificacao") Notificacao notificacao) {
        notificacaoService.save(notificacao);
        return "redirect:/notificacoes";  
    }
    @GetMapping("/formAlterarNotificacao/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Notificacao notificacao = notificacaoService.getNotificacaoById(id);
        model.addAttribute("notificacao", notificacao);
        return "alterar_notificacao";  
    }

    @GetMapping("/apagarNotificacao/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.notificacaoService.apagarNotificacaoById(id);
        return "redirect:/notificacoes";  
    }

}
