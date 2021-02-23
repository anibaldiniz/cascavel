package br.unioeste.cascavel.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean email;
    private Boolean celularApp;                //aplicativo desenvolvido para receber a notificação
    private Boolean celularAplicativoExterno;  //telegran, signal, whattsapp  e etc
    

    @NonNull
    @ManyToOne
    @JoinColumn(name = "usuarioRecebeu_id")
    private Usuario usuarioRecebeu;            //quem vai receber

    @NonNull
    @ManyToOne
    @JoinColumn(name = "usuarioEnviou_id")
    private Usuario usuarioEnviou;

    @NonNull
    private LocalDateTime dataEnvio;

    @NonNull
    private String mensagemNoticacao;   //texto que destaca resumidadmente uma informação

}
