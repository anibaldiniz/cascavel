package br.unioeste.cascavel.model.solicitacao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import br.unioeste.cascavel.model.Documento;
import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.model.Unidade;
import br.unioeste.cascavel.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
public class Solicitacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String titulo;

    @NonNull 
    @OneToOne
    @JoinColumn(name="solicitacaoTipo_id")
    private SolicitacaoTipo tipo;

    @OneToOne
    @JoinColumn(name="solicitacao_id")
    private Solicitacao solicitacaoPai; //quando há uma necessidade de reabrir, adequar e seguir novamente a tramitação

    @NonNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; //solicitante

    @ManyToOne
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @NonNull
    private LocalDateTime dataInicio;

    @NonNull
    private LocalDateTime dataFim;

    @NonNull
    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Status> status;     //tramitando, cancelado, suspenso, concluido - com quem fez a alteração e campo de observação

    @NonNull
    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;


    private String Observacao;

}
