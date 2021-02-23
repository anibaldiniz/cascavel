package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Localizacao;
import br.unioeste.cascavel.model.Unidade;
import br.unioeste.cascavel.model.Usuario;

public interface LocalizacoesService {

    void save(Localizacao localizacao);

    Localizacao getLocalizacaoById(long id);

    void apagarLocalizacaoById(long id);

    List<Localizacao> getAllLocalizacoes();

	List<Usuario> getAllUsuarios(long id_localizacao);
	List<Grupo> getAllGrupos(long id_localizacao);
	List<Unidade> getAllUnidades(long id_localizacao);
}
