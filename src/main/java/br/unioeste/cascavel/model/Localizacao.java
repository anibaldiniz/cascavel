package br.unioeste.cascavel.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Localizacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localizacao;  //localização com um JSON: https://www.devmedia.com.br/como-utilizar-a-google-geocoding-api-para-obter-enderecos/36751
    private Double latitude;
    private Double longitude;
    private Double altitude;

    @OneToMany(mappedBy = "localizacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos;

    @ManyToMany(mappedBy = "localizacoes")
    private List<Unidade> unidades;

    @ManyToMany(mappedBy = "localizacoes")
    private List<Usuario> usuarios;


}
