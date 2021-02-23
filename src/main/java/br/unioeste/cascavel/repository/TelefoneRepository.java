package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
    
}
