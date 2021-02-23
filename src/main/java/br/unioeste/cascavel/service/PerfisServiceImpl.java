package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Perfil;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.PerfilRepository;

@Service
public class PerfisServiceImpl implements PerfisService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public List<Perfil> getAllPerfis() {
        return perfilRepository.findAll();
    }

    @Override
    public void save(Perfil perfil) {
        perfilRepository.save(perfil);

    }

    @Override
    public Perfil getPerfilById(long id) {
        Optional<Perfil> opcional = perfilRepository.findById(id);
        Perfil perfil = null;
        if (opcional.isPresent()) {
            perfil = opcional.get();
        } else {
            throw new RuntimeException("Perfil não encontrado");
        }
        return perfil;
    }

    @Override
    public void apagarPerfilById(long id) {
        this.perfilRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios(long id_perfil) {
        Optional<Perfil> perfil = perfilRepository.findById(id_perfil);
        List<Usuario> usuarios = perfil.get().getUsuarios();
        return usuarios;
    }

}
