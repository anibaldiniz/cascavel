package br.unioeste.cascavel.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
@Entity
public class Email {
    @Id
    private String email;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioEmails; 
}
