package br.unioeste.cascavel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.unioeste.cascavel.model.Email;
import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.model.Perfil;
import br.unioeste.cascavel.model.Telefone;
import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.service.CategoriasService;
import br.unioeste.cascavel.service.EmailsService;
import br.unioeste.cascavel.service.EventosService;
import br.unioeste.cascavel.service.PerfisService;
import br.unioeste.cascavel.service.TelefonesService;
import br.unioeste.cascavel.service.UnidadesService;
import br.unioeste.cascavel.service.UsuariosService;

@SpringBootTest
class CascavelApplicationTests {

	@Autowired
	UsuariosService usuarioService;

	@Autowired
	PerfisService perfilService;

	@Autowired
	UnidadesService unidadeService;

	@Autowired
	EventosService eventoService;

	@Autowired
	EmailsService emailService;

	@Autowired
	CategoriasService categoriaService;

	@Autowired
	TelefonesService telefoneService;

	
	@Test
	void contextLoads() {

		List<Telefone> telefones = telefoneService.getAllTelefones();
		for (Telefone telefone : telefones) {
			System.out.println(telefone.getPrefixo() + "-" + telefone.getSufixo());
		}

		List<Email> emails = emailService.getAllEmails();
		for (Email email : emails) {
			System.out.println(email.getEmail());
		}

		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.getNome() + "-" + usuario.getId());
		}

		Evento evento = new Evento();
		evento.setServico("teste");
		evento.setData(LocalDateTime.now());
		eventoService.save(evento);

	}

	@Test
	void testeInclusao() {
		List<Evento> eventos = eventoService.getAllEventos();
		for (Evento evento2 : eventos) {

			System.out.println(evento2.getServico());
		}
	}

	@Test
	void testeIncluirUsuario() {

		// Usuario usuario = usuarioService.getUsuarioById(5);
		Usuario usuario = new Usuario();
		usuario.setNome("anibal.diniz");
		usuario.setNomeCompleto("Anibal mantovani Diniz");

		Email email1 = new Email();
		email1.setEmail("anibal.diniz@unioeste.br");
		email1.setUsuarioEmails(usuario);

		Email email2 = new Email();
		email2.setEmail("anibaldiniz@gmail.com");
		email2.setUsuarioEmails(usuario);

		List<Email> emails = new LinkedList<>();
		emails.add(email1);
		emails.add(email2);

		usuario.setEmails(emails);
		usuarioService.save(usuario);
	}

	@Test
	void testeIncluirPerfil() {
		
	    //usuarioService.deleteAll();
		

		Usuario usuario = new Usuario();
		usuario.setNome("anibal.diniz");
		usuario.setNomeCompleto("Anibal mantovani Diniz");
		List<Perfil> perfis = perfilService.getPerfilByNome("Usuário");
		Perfil perfil =null;
		if (perfis.size()>0) {
			perfil = perfis.get(0);
		}else{
			perfil = new Perfil();
			perfil.setNome("Usuário");
		}
	    
		perfilService.save(perfil);
		
		usuario.setPerfil(perfil);

		usuarioService.save(usuario);
	}

}
