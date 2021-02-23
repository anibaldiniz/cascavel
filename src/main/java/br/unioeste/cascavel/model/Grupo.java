package br.unioeste.cascavel.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
/* 
    Este grupo será o responsável para armazenar as seções e setores
*/
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome; 

    @ManyToMany(mappedBy = "grupos", cascade = CascadeType.ALL)
    private List<Usuario> responsaveis;   //para colocar o que poderá redistribuir as solicitações e tarefas
    
    @ManyToMany(mappedBy = "grupos", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @ManyToMany(mappedBy = "grupos", cascade = CascadeType.ALL)
    private List<Unidade> unidades;

    @ManyToOne
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    
    
}
