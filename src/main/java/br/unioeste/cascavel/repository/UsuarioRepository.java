package br.unioeste.cascavel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(value = "SELECT * FROM Usuario  WHERE nome like ?1", nativeQuery=true)
	Usuario findByName(String nome);

    List<Usuario> findByNomeCompleto(String nomeCompleto);

    @Query(value = "SELECT * FROM Usuario  WHERE nome like %?1% order by nome", nativeQuery=true)
    List<Usuario> findByNomeLike(String nome);

    
}
