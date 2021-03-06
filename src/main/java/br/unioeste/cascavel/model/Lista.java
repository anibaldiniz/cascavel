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
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Lista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeLista;   //é o nme da lista que pode ser utilizada para envio de comunicação pelo outlook da instituição

    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "listas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lista> listas;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;


}
