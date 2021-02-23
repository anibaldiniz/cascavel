package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Documento;

public interface DocumentosService {
    
    List<Documento> getAllDocumentos();
    void save(Documento documento);
    Documento getDocumentoById(long id);
    void apagarDocumentoById(long id);
	List<Documento> getAllDocumentos(long id_solicitacao);
}
