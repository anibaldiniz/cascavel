package br.unioeste.cascavel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	List<Perfil> findByNome(String nome);    
}
