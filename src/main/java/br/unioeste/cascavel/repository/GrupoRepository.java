package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    
}
