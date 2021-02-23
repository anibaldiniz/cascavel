package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    
}
