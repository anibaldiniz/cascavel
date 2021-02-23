package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.repository.EventoRepository;

@Service
public class EventosServiceImpl implements EventosService {

    @Autowired
    private EventoRepository emailRepository;

    @Override
    public List<Evento> getAllEventos() {
        return emailRepository.findAll();
    }

    @Override
    public void save(Evento email) {
        emailRepository.save(email);

    }

    @Override
    public Evento getEventoById(long id) {
        Optional<Evento> opcional = emailRepository.findById(id);
        Evento email = null;
        if (opcional.isPresent()) {
            email = opcional.get();
        } else {
            throw new RuntimeException("Evento não encontrado");
        }
        return email;
    }

    @Override
    public void apagarEventoById(long id) {
        this.emailRepository.deleteById(id);
    }

}
