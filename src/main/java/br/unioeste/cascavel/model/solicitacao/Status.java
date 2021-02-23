package br.unioeste.cascavel.model.solicitacao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.unioeste.cascavel.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean tramitando; //valor default
    private Boolean cancelado;
    private Boolean suspenso;
    private Boolean concluido;

    private LocalDateTime dataAlteracaoStatus;

    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuarioAutor;

    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;
    
    private String Observacao;
    //tramitando, cancelado, suspenso, concluido  

}
