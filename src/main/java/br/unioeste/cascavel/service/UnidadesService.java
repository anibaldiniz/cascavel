package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Localizacao;
import br.unioeste.cascavel.model.Unidade;

public interface UnidadesService {

    void save(Unidade unidade);

    Unidade getUnidadeById(long id);

    void apagarUnidadeById(long id);

    List<Unidade> getAllUnidades();

	List<Localizacao> getAllLocalizacoes(long id_unidade);

	List<Grupo> getAllGrupos(long id_unidade);

    Unidade getUnidadeByNome(String departamento);
}
