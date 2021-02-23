package br.unioeste.cascavel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Categoria;
import br.unioeste.cascavel.model.Usuario;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    @Query(value = "SELECT u.id, u.nome, u.senha, u.nome_completo, u.primeiro_nome, u.lingua,u.esta_ativo,u.excluido_ldap,u.ultimo_login, u.ultima_alteracao_cadastro FROM Usuario as u , Usuario_Categoria as uc WHERE uc.usuario_id = u.id AND uc.categoria_id = ?1", nativeQuery=true)
	List<Usuario> findAllUsuarios(long id_categoria);
	
}
