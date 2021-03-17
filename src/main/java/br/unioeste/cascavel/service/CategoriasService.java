package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Categoria;
import br.unioeste.cascavel.model.Lista;
import br.unioeste.cascavel.model.Usuario;

public interface CategoriasService {
    
    List<Categoria> getAllCategorias();
    void save(Categoria categoria);
    Categoria getCategoriaById(long id);
    void apagarCategoriaById(long id);
	List<Usuario> getAllUsuarios(long id_categoria);
    List<Lista> getAllListas(long id_categoria);
    Categoria getCategoriaByNome(String oUCategoria);
}
