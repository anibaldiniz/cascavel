package br.unioeste.cascavel.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.unioeste.cascavel.model.solicitacao.Solicitacao;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor

@Data
@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String senha;
    private String nomeCompleto;
    private String primeiroNome;
    private String lingua;
    private Boolean estaAtivo;    
    private Boolean excluidoLdap;    
    private LocalDateTime ultimoLogin;    
    private LocalDateTime ultimaAlteracaoCadastro;  

    
    public  Usuario(BigInteger id, String nome, String senha, String nomeCompleto, String primeiroNome, String lingua, Boolean estaAtivo, Boolean excluidoLdap, LocalDateTime ultimoLogin, LocalDateTime ultimaAlteracaoCadastro) {
        this.id = id.longValue();
        this.nome = nome;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.primeiroNome = primeiroNome;
        this.lingua = lingua;
        this.estaAtivo = estaAtivo;
        this.excluidoLdap = excluidoLdap;
        this.ultimoLogin = ultimoLogin;
        this.ultimaAlteracaoCadastro = ultimaAlteracaoCadastro;

    }

    @ManyToMany
    @JoinTable(name = "usuario_telefone",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="telefone_id"))
    private List<Telefone> telefones;
    
    @ManyToMany
    @JoinTable(name = "usuario_localizacao",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="localizacao_id"))
    private List<Localizacao> localizacoes;
    
    @ManyToMany
    @JoinTable(name = "usuario_grupo",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="grupo_id"))
    private List<Grupo> grupos;

    @ManyToMany
    @JoinTable(name = "usuario_responsavel_grupo",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="grupo_id"))
    private List<Grupo> responsaveis;
    
    @ManyToMany
    @JoinTable(name = "usuario_categoria",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="categoria_id"))
    private List<Categoria> categorias;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @OneToMany(mappedBy = "usuarioRecebeu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoesRecebidas;

    @OneToMany(mappedBy = "usuarioEmails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email> emails;


    @OneToMany(mappedBy = "usuarioEnviou", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoesEnviadas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitacao> solicitacoes;
    
    
    
    //chefia
    //setores


    
    
}
