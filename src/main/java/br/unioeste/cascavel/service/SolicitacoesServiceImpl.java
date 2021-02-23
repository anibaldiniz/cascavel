package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.model.solicitacao.Status;
import br.unioeste.cascavel.repository.SolicitacaoRepository;

@Service
public class SolicitacoesServiceImpl implements SolicitacoesService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Override
    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    @Override
    public void save(Solicitacao solicitacao) {
        solicitacaoRepository.save(solicitacao);

    }

    @Override
    public Solicitacao getSolicitacaoById(long id) {
        Optional<Solicitacao> opcional = solicitacaoRepository.findById(id);
        Solicitacao solicitacao = null;
        if (opcional.isPresent()) {
            solicitacao = opcional.get();
        } else {
            throw new RuntimeException("Solicitacao não encontrada");
        }
        return solicitacao;
    }

    @Override
    public void apagarSolicitacaoById(long id) {
        this.solicitacaoRepository.deleteById(id);
    }

    @Override
    public List<Status> getAllStatus(long id) {
        Optional<Solicitacao> opcional = solicitacaoRepository.findById(id);
        List<Status> status = opcional.get().getStatus();
        return status;
    }

    @Override
    public List<Evento> getAllEventos(long id) {
        Optional<Solicitacao> opcional = solicitacaoRepository.findById(id);
        List<Evento> eventos = opcional.get().getEventos();
        return eventos;
    }

    @Override
    public List<Documento> getAllDocumentos(long id) {
        Optional<Solicitacao> opcional = solicitacaoRepository.findById(id);
        List<Documento> documentos = opcional.get().getDocumentos();
        return documentos;
    }

   

}
