package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Perfil;
import br.unioeste.cascavel.model.Usuario;

public interface PerfisService {

    void save(Perfil perfil);

    Perfil getPerfilById(long id);
    
    List<Perfil> getPerfilByNome(String nome);

    void apagarPerfilById(long id);

    List<Perfil> getAllPerfis();

	List<Usuario> getAllUsuarios(long id_perfil);
}
