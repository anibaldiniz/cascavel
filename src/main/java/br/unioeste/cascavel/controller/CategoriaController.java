package br.unioeste.cascavel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.unioeste.cascavel.model.Categoria;
import br.unioeste.cascavel.service.CategoriasService;

@Controller
public class CategoriaController {
    
    @Autowired
    private CategoriasService categoriaService;

    @GetMapping("/categorias")
    public String viewHomePage(Model model){
        model.addAttribute("ListaCategorias", categoriaService.getAllCategorias());
        return "categorias";
    }
    @GetMapping("/formIncluirCategoria")
    public String formIncluirCategoria(Model model){
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "nova_categoria";
    }

    @PostMapping("/incluirCategoria")
    public String incluir(@ModelAttribute("categoria") Categoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/categorias";  
    }
    @GetMapping("/formAlterarCategoria/{id}")
    public String alterar(@PathVariable (value = "id") long id, Model model) {
        Categoria categoria = categoriaService.getCategoriaById(id);
        model.addAttribute("categoria", categoria);
        return "alterar_categoria";  
    }

    @GetMapping("/apagarCategoria/{id}")
    public String apagar(@PathVariable (value = "id") long id, Model model) {
        this.categoriaService.apagarCategoriaById(id);
        return "redirect:/categorias";  
    }

    @GetMapping("/usuariosCategoria/{id}")
    public String visualizeUsuarios(@PathVariable (value = "id") long id_categoria, Model model){
        model.addAttribute("ListaUsuarios", categoriaService.getAllUsuarios(id_categoria));
        String nomeCategoria = categoriaService.getCategoriaById(id_categoria).getNome();
        model.addAttribute("nome_categoria", nomeCategoria);
        return "usuarios_categoria";
    }

}
