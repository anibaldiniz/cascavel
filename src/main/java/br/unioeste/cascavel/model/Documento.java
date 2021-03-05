package br.unioeste.cascavel.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.boot.context.properties.ConfigurationProperties;

import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@ConfigurationProperties(prefix = "url")
@Data
@Entity
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caminho;
    private String extensao;
    private String mimeType;
    private String nome;
    private String conteudo;
    private LocalDateTime dataInclusao;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;     //quem incluiu o documento

    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;     //quem incluiu o documento

}
