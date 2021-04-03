package br.unioeste.cascavel.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.service.UsuariosService;

@Controller
public class UsuarioController {
    
    @Autowired
    private UsuariosService usuarioService;


    @GetMapping("/usuariosTodos")
    public String viewHomePage(Model model){
        model.addAttribute("ListaUsuarios", usuarioService.getAllUsuarios());
        return "usuariosTodos";
    }
    @RequestMapping("/usuarioNome")
    public String alterar(@RequestParam(value = "nome", required = true) String nome, Model model) {
        List<Usuario> usuarios = usuarioService.getUsuarioByNameLike(nome.toLowerCase().trim());
        model.addAttribute("ListaUsuarios", usuarios);
        return "usuariosPesquisa";  
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

    // @GetMapping("/importarUsuariosLdap")
    // public String geNovosUsuariosLdap(Model model) {
    //     Boolean resultado = usuarioService.importarUsuariosLdap();
    //     if (resultado) {
    //         model.addAttribute("resultado", "Importados!");
    //     } else {
    //         model.addAttribute("resultado", "Erro durante a Importação!!!");
    //     }
    //     return "importarUsuariosLdap";
    // }

    
    @RequestMapping(value = "/listUsuarios", method = RequestMethod.GET)
    public String listUsuarios(Model model, @RequestParam("page") Optional<Integer> page, 
      @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Usuario> usuarioPage = usuarioService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("usuarioPage", usuarioPage);

        int totalPages = usuarioPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "usuarios";
    }

}
