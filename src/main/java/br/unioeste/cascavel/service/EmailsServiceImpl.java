package br.unioeste.cascavel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Email;
import br.unioeste.cascavel.repository.EmailRepository;

@Service
public class EmailsServiceImpl implements EmailsService {

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @Override
    public void save(Email email) {
        emailRepository.save(email);

    }

    @Override
    public Email getEmailById(long id) {
        Optional<Email> opcional = emailRepository.findById(id);
        Email email = null;
        if (opcional.isPresent()) {
            email = opcional.get();
        } else {
            throw new RuntimeException("Email não encontrado");
        }
        return email;
    }

    @Override
    public void apagarEmailById(long id) {
        this.emailRepository.deleteById(id);
    }

}
