package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.UsuarioRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);

    }

    @Override
    public Usuario getUsuarioById(long id) {
        Optional<Usuario> opcional = usuarioRepository.findById(id);
        Usuario usuario = null;
        if (opcional.isPresent()) {
            usuario = opcional.get();
        } else {
            throw new RuntimeException("Usuario não encontrado");
        }
        return usuario;
    }

    @Override
    public void apagarUsuarioById(long id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario getUsuarioByName(String nome) {
        return usuarioRepository.findByName(nome);
    }


    // @Override
    // public List<Usuario> getAllUsuarios(long id_usuario) {
    //     Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);
    //     List<Usuario> usuarios = usuario.get().getUsuarios();
    //     return usuarios;
    // }

    
    
}
