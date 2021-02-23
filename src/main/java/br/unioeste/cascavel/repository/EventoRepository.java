package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}
