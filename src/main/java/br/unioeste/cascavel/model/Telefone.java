package br.unioeste.cascavel.model;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Byte codPais;
    private Byte ddd;
    private String prefixo;  //primeiros 5 ou quatro digitos 
    private String sufixo;   //últimos 4 digitos
    private Boolean fixo;   
    private Boolean ramal;  //se for ramal, o codigo do pais, o ddd e o prefixo serão vazios

    @ManyToMany(mappedBy = "telefones")
    private List<Usuario> usuarios; 
}
