package br.unioeste.cascavel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unioeste.cascavel.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    //@Query(value = "SELECT * FROM Telefone WHERE ddd=?1 and prefixo=?2 and sufixo=?3", nativeQuery=true)
    Telefone findByDddAndPrefixoAndSufixo(Byte ddd, String prefixo, String sufixo);
    
}
