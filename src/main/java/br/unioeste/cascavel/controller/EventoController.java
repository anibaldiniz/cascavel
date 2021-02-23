package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.service.EventosService;

@Controller
public class EventoController {
    
    @Autowired
    private EventosService eventoService;

    @GetMapping("/eventos")
    public String viewHomePage(Model model){
        model.addAttribute("ListaEventos", eventoService.getAllEventos());
        return "eventos";
    }
    @GetMapping("/formIncluirEvento")
    public String formIncluirEvento(Model model){
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        return "nova_evento";
    }

    @PostMapping("/incluirEvento")
    public String incluir(@ModelAttribute("evento") Evento evento) {
        eventoService.save(evento);
        return "redirect:/eventos";  
    }
    @GetMapping("/formAlterarEvento/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Evento evento = eventoService.getEventoById(id);
        model.addAttribute("evento", evento);
        return "alterar_evento";  
    }

    @GetMapping("/apagarEvento/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.eventoService.apagarEventoById(id);
        return "redirect:/eventos";  
    }
}
