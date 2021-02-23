package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Localizacao;
import br.unioeste.cascavel.model.Unidade;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.LocalizacaoRepository;

@Service
public class LocalizacoesServiceImpl implements LocalizacoesService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Override
    public List<Localizacao> getAllLocalizacoes() {
        return localizacaoRepository.findAll();
    }

    @Override
    public void save(Localizacao localizacao) {
        localizacaoRepository.save(localizacao);

    }

    @Override
    public Localizacao getLocalizacaoById(long id) {
        Optional<Localizacao> opcional = localizacaoRepository.findById(id);
        Localizacao localizacao = null;
        if (opcional.isPresent()) {
            localizacao = opcional.get();
        } else {
            throw new RuntimeException("Localização não encontrada");
        }
        return localizacao;
    }

    @Override
    public void apagarLocalizacaoById(long id) {
        this.localizacaoRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios(long id_localizacao) {
        Optional<Localizacao> localizacao = localizacaoRepository.findById(id_localizacao);
        List<Usuario> usuarios = localizacao.get().getUsuarios();
        return usuarios;
    }

    @Override
    public List<Grupo> getAllGrupos(long id_localizacao) {
        Optional<Localizacao> localizacao = localizacaoRepository.findById(id_localizacao);
        List<Grupo> grupos = localizacao.get().getGrupos();
        return grupos;
    }

    @Override
    public List<Unidade> getAllUnidades(long id_localizacao) {
        Optional<Localizacao> localizacao = localizacaoRepository.findById(id_localizacao);
        List<Unidade> unidades = localizacao.get().getUnidades();
        return unidades;
    }

}
