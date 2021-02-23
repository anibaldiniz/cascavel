package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Usuario;

public interface UsuariosService {

    List<Usuario> getAllUsuarios();
    void save(Usuario usuario);
    Usuario getUsuarioById(long id);
    void apagarUsuarioById(long id);
	// List<Usuario> getAllUsuarios(long id_categoria);
}
