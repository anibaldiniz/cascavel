package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Evento;

public interface EventosService {

    void save(Evento email);

    Evento getEventoById(long id);

    void apagarEventoById(long id);

    List<Evento> getAllEventos();
}
