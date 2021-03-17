package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Categoria;
import br.unioeste.cascavel.model.Lista;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.CategoriaRepository;

@Service
public class CategoriasServiceImpl implements CategoriasService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);

    }

    @Override
    public Categoria getCategoriaById(long id) {
        Optional<Categoria> opcional = categoriaRepository.findById(id);
        Categoria categoria = null;
        if (opcional.isPresent()) {
            categoria = opcional.get();
        } else {
            throw new RuntimeException("Categoria não encontrada");
        }
        return categoria;
    }

    @Override
    public void apagarCategoriaById(long id) {
        this.categoriaRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios(long id_categoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(id_categoria);
        List<Usuario> usuarios = categoria.get().getUsuarios();
        return usuarios;
    }

    @Override
    public List<Lista> getAllListas(long id_categoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(id_categoria);
        List<Lista> listas = categoria.get().getListas();
        return listas;
    }

    @Override
    public Categoria getCategoriaByNome(String OUCategoria) {
        return categoriaRepository.findByNome(OUCategoria);
    }
    
}
