package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.model.solicitacao.Status;

public interface SolicitacoesService {

    void save(Solicitacao solicitacao);

    Solicitacao getSolicitacaoById(long id);

    void apagarSolicitacaoById(long id);

    List<Status> getAllStatus(long id);
    List<Solicitacao> getAllSolicitacoes();
    List<Evento> getAllEventos(long id);
    List<Documento> getAllDocumentos(long id);
}
