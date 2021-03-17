package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Grupo;
import br.unioeste.cascavel.model.Localizacao;
import br.unioeste.cascavel.model.Unidade;
import br.unioeste.cascavel.repository.UnidadeRepository;

@Service
public class UnidadesServiceImpl implements UnidadesService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Override
    public List<Unidade> getAllUnidades() {
        return unidadeRepository.findAll();
    }

    @Override
    public void save(Unidade unidade) {
        unidadeRepository.save(unidade);

    }

    @Override
    public Unidade getUnidadeById(long id) {
        Optional<Unidade> opcional = unidadeRepository.findById(id);
        Unidade unidade = null;
        if (opcional.isPresent()) {
            unidade = opcional.get();
        } else {
            throw new RuntimeException("Unidade não encontrada");
        }
        return unidade;
    }

    @Override
    public void apagarUnidadeById(long id) {
        this.unidadeRepository.deleteById(id);
    }

    @Override
    public List<Localizacao> getAllLocalizacoes(long id_unidade) {
        Optional<Unidade> unidade = unidadeRepository.findById(id_unidade);
        List<Localizacao> localizacoes = unidade.get().getLocalizacoes();
        return localizacoes;
    }

    @Override
    public List<Grupo> getAllGrupos(long id_unidade) {
        Optional<Unidade> unidade = unidadeRepository.findById(id_unidade);
        List<Grupo> grupos = unidade.get().getGrupos();
        return grupos;
    }

    @Override
    public Unidade getUnidadeByNome(String departamento) {
        return unidadeRepository.findByNome(departamento);
    }

    

}
