package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    
}
