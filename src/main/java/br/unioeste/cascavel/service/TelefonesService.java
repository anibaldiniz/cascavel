package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Telefone;
import br.unioeste.cascavel.model.Usuario;

public interface TelefonesService {

    void save(Telefone telefone);

    Telefone getTelefoneById(long id);

    void apagarTelefoneById(long id);

    List<Telefone> getAllTelefones();

	List<Usuario> getAllUsuarios(long id_telefone);

    Telefone getTelefoneByNumero(Byte ddd, String prefixo, String sufixo);
}
