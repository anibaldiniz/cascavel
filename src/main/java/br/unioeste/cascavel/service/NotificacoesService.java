package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Notificacao;

public interface NotificacoesService {

    void save(Notificacao notificacao);

    Notificacao getNotificacaoById(long id);

    void apagarNotificacaoById(long id);

    List<Notificacao> getAllNotificacoes();
}
