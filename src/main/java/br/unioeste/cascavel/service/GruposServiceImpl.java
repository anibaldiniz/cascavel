package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.GrupoRepository;

@Service
public class GruposServiceImpl implements GruposService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public List<Grupo> getAllGrupos() {
        return grupoRepository.findAll();
    }

    @Override
    public void save(Grupo grupo) {
        grupoRepository.save(grupo);

    }

    @Override
    public Grupo getGrupoById(long id) {
        Optional<Grupo> opcional = grupoRepository.findById(id);
        Grupo grupo = null;
        if (opcional.isPresent()) {
            grupo = opcional.get();
        } else {
            throw new RuntimeException("Grupo não encontrado");
        }
        return grupo;
    }

    @Override
    public void apagarGrupoById(long id) {
        this.grupoRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios(long id_grupo) {
        Optional<Grupo> grupo = grupoRepository.findById(id_grupo);
        List<Usuario> usuarios = grupo.get().getUsuarios();
        return usuarios;
    }
    
    @Override
    public List<Usuario> getAllResponsaveisGrupo(long id_grupo) {
        Optional<Grupo> grupo = grupoRepository.findById(id_grupo);
        List<Usuario> usuarios = grupo.get().getResponsaveis();
        return usuarios;
    }
    

}
