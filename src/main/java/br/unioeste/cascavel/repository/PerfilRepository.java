package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {


	@Query(value = "SELECT * FROM Perfil WHERE nome like ?1", nativeQuery=true)
	Perfil findByNome(String nome);
    
}
