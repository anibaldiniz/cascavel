package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.service.UsuariosService;

@Controller
public class UsuarioController {
    
    @Autowired
    private UsuariosService usuarioService;


    @GetMapping("/usuarios")
    public String viewHomePage(Model model){
        model.addAttribute("ListaUsuarios", usuarioService.getAllUsuarios());
        return "usuarios";
    }
    @GetMapping("/formIncluirUsuario")
    public String formIncluirUsuario(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "novo_usuario";
    }

    @PostMapping("/incluirUsuario")
    public String incluir(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios";  
    }
    @GetMapping("/formAlterarUsuario/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        return "alterar_usuario";  
    }

    @GetMapping("/apagarUsuario/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.usuarioService.apagarUsuarioById(id);
        return "redirect:/usuarios";  
    }

    @GetMapping("/emailsUsuario/{id}")
    public String visualizeEmails(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("ListaEmails", usuarioService.getUsuarioById(id).getEmails());
        model.addAttribute("usuario", usuarioService.getUsuarioById(id));
        return "usuarios_emails";
    }

    @GetMapping("/telefonesUsuario/{id}")
    public String visualizeTelefones(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("ListaTelefones", usuarioService.getUsuarioById(id).getTelefones());
        model.addAttribute("usuario", usuarioService.getUsuarioById(id));
        return "telefones_usuarios";
    }
    @GetMapping("/categoriasUsuario/{id}")
    public String visualizeCategorias(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("ListaCategorias", usuarioService.getUsuarioById(id).getCategorias());
        model.addAttribute("usuario", usuarioService.getUsuarioById(id));
        return "categorias_usuarios";
    }
    @GetMapping("/gruposUsuario/{id}")
    public String visualizeGrposDoUsuario(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("ListaGrupos", usuarioService.getUsuarioById(id).getGrupos());
        model.addAttribute("usuario_nome", usuarioService.getUsuarioById(id).getNome());
        return "usuarios_grupo";
    }

}
