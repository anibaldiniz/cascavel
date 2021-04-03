package br.unioeste.cascavel.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.unioeste.cascavel.model.Usuario;

public interface UsuariosService {
    List<Usuario> getAllUsuarios();
    void save(Usuario usuario);
    void deleteAll();
    Usuario getUsuarioById(long id);
    void apagarUsuarioById(long id);
    Usuario getUsuarioByName(String nome);
    Page<Usuario> findPaginated(Pageable pageable);
	// List<Usuario> getAllUsuarios(long id_categoria);
    List<Usuario> getUsuarioByNameLike(String nome);
}
