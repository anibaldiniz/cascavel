package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import br.unioeste.cascavel.repository.DocumentoRepository;
import br.unioeste.cascavel.repository.SolicitacaoRepository;

@Service
public class DocumentosServiceImpl implements DocumentosService {

    @Autowired
    private DocumentoRepository documentoRepository;
    
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Override
    public List<Documento> getAllDocumentos() {
        return documentoRepository.findAll();
    }

    @Override
    public void save(Documento documento) {
        documentoRepository.save(documento);

    }

    @Override
    public Documento getDocumentoById(long id) {
        Optional<Documento> opcional = documentoRepository.findById(id);
        Documento documento = null;
        if (opcional.isPresent()) {
            documento = opcional.get();
        } else {
            throw new RuntimeException("Documento não encontrado");
        }
        return documento;
    }

    @Override
    public void apagarDocumentoById(long id) {
        this.documentoRepository.deleteById(id);
    }


    @Override
    public List<Documento> getAllDocumentos(long id_solicitacao) {
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id_solicitacao);
        List<Documento> documentos = solicitacao.get().getDocumentos();
        return documentos;
    }

    
    
}
