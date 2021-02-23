package br.unioeste.cascavel.model.solicitacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    O tipo de solicitação é para facilitar ao usuário escolher para onde vai o serviço, ou seja
    inverte-se a responsabilidade, o usuário não tem que saber para quem enviar. O sistema fará isso
    de forma automática.

*/
@NoArgsConstructor
@Data
@Entity
public class SolicitacaoSubTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "solicitacaoTipo_id")
    private SolicitacaoTipo solicitacaoTipo;
    
    private String Observacao;

}
