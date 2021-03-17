package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Telefone;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.TelefoneRepository;

@Service
public class TelefonesServiceImpl implements TelefonesService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Override
    public List<Telefone> getAllTelefones() {
        return telefoneRepository.findAll();
    }

    @Override
    public void save(Telefone telefone) {
        telefoneRepository.save(telefone);

    }

    @Override
    public Telefone getTelefoneById(long id) {
        Optional<Telefone> opcional = telefoneRepository.findById(id);
        Telefone telefone = null;
        if (opcional.isPresent()) {
            telefone = opcional.get();
        } else {
            throw new RuntimeException("Telefone não encontrado");
        }
        return telefone;
    }

    @Override
    public void apagarTelefoneById(long id) {
        this.telefoneRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios(long id_telefone) {
        Optional<Telefone> telefone = telefoneRepository.findById(id_telefone);
        List<Usuario> usuarios = telefone.get().getUsuarios();
        return usuarios;
    }

    @Override
    public Telefone getTelefoneByNumero(Byte ddd, String prefixo, String sufixo) {
        return telefoneRepository.getTelefoneByNumero(ddd, prefixo, sufixo);
    }

    

}
