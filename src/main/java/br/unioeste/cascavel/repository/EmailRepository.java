package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    
}
