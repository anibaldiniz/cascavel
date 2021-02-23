package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Usuario;

public interface GruposService {

    void save(Grupo grupo);

    Grupo getGrupoById(long id);

    void apagarGrupoById(long id);

    List<Grupo> getAllGrupos();


    List<Usuario> getAllUsuarios(long id_grupo);
    
    List<Usuario> getAllResponsaveisGrupo(long id_grupo);
}
