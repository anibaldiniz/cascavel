package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Notificacao;
import br.unioeste.cascavel.repository.NotificacaoRepository;

@Service
public class NotificacoesServiceImpl implements NotificacoesService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Override
    public List<Notificacao> getAllNotificacoes() {
        return notificacaoRepository.findAll();
    }

    @Override
    public void save(Notificacao notificacao) {
        notificacaoRepository.save(notificacao);

    }

    @Override
    public Notificacao getNotificacaoById(long id) {
        Optional<Notificacao> opcional = notificacaoRepository.findById(id);
        Notificacao notificacao = null;
        if (opcional.isPresent()) {
            notificacao = opcional.get();
        } else {
            throw new RuntimeException("Notificação não encontrada");
        }
        return notificacao;
    }

    @Override
    public void apagarNotificacaoById(long id) {
        this.notificacaoRepository.deleteById(id);
    }

}
