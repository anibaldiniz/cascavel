package br.unioeste.cascavel.model.solicitacao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import br.unioeste.cascavel.model.Grupo;
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
public class SolicitacaoTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @NonNull
    @OneToMany(mappedBy = "solicitacaoTipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitacaoSubTipo> subTipos;
    
    private String Observacao;

}
