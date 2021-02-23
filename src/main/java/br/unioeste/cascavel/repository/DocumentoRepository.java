package br.unioeste.cascavel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Query("Select a from Documento a where usuario_id = ?1 and solicitacao_id = ?2")
    List<Documento> documentosSolicitacao(Long usuarioID, Long solicitacao);

    @Query("Select conteudo from Documento a where usuario_id = ?1 and solicitacao_id = ?2")
    List<String> caminhoDocumentosSolicitacao(Long usuarioId, Long solicitacao);
    
    @Query("Select a from Documento a where nome = ?1")
    Documento getByNome(String nomeArquivo);
    
}