package br.unioeste.cascavel.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
/*
    Uma unidade exprime algum setor físico ou administrativo;
    Exemplo: Prédio, Laboratório, Colegiado, Centro, Setor
*/
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
                            //localização com um JSON: https://www.devmedia.com.br/como-utilizar-a-google-geocoding-api-para-obter-enderecos/36751
    private String nome;
    private String cidade;
    private String denominacaoAbreviadaInstitucional;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Unidade unidadePai;

    @ManyToMany
    @JoinTable(name = "unidade_localizacao",
        joinColumns = @JoinColumn(name="unidade_id"),
        inverseJoinColumns = @JoinColumn(name="localizacao_id"))
    private List<Localizacao> localizacoes;
    
    @ManyToMany
    @JoinTable(name = "unidade_grupo",
        joinColumns = @JoinColumn(name="unidade_id"),
        inverseJoinColumns = @JoinColumn(name="grupo_id"))
    private List<Grupo> grupos;


  
    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios;
    



}
