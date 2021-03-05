package br.unioeste.cascavel.service;

import java.util.List;

import br.unioeste.cascavel.model.Email;

public interface EmailsService {
    
    void save(Email email);
    Email getEmailById(String id);
    void apagarEmailById(String id);
	List<Email> getAllEmails();
}
