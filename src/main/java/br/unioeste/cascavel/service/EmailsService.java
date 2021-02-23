package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Email;

public interface EmailsService {
    
    void save(Email email);
    Email getEmailById(long id);
    void apagarEmailById(long id);
	List<Email> getAllEmails();
}
