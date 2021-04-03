package br.unioeste.cascavel;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import br.unioeste.cascavel.model.Categoria;
import br.unioeste.cascavel.model.Email;
import br.unioeste.cascavel.model.Evento;
import br.unioeste.cascavel.model.Perfil;
import br.unioeste.cascavel.model.Telefone;
import br.unioeste.cascavel.model.Unidade;
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

	@Autowired
	private Environment env;

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

		// usuarioService.deleteAll();

		Usuario usuario = new Usuario();
		usuario.setNome("anibal.diniz");
		usuario.setNomeCompleto("Anibal mantovani Diniz");
		List<Perfil> perfis = perfilService.getPerfilByNome("Usuário");
		Perfil perfil = null;
		if (perfis.size() > 0) {
			perfil = perfis.get(0);
		} else {
			perfil = new Perfil();
			perfil.setNome("Usuário");
		}

		perfilService.save(perfil);

		usuario.setPerfil(perfil);

		usuarioService.save(usuario);
	}

	@Bean
	public LdapContextSource contextSource() {
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(env.getRequiredProperty("ldap.url"));
		contextSource.setBase(env.getRequiredProperty("ldap.partitionSuffix"));
		contextSource.setUserDn(env.getRequiredProperty("ldap.principal"));
		contextSource.setPassword(env.getRequiredProperty("ldap.password"));
		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}

	public DirContext authenticate(String username, String password) {
		DirContext dir = contextSource()
				.getContext("cn=" + username + ",ou=user," + env.getRequiredProperty("ldap.partitionSuffix"), password);
		return dir;
	}

	@Test
	void getUserDetail() {
		DirContext ctx = null;
		try {
			ctx = context();
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "cn", "samaccountname", "givenName", "department", "mail", "extensionattribute14",
					"distinguishedname", "extensionattribute15", "ipphone","memberof" };// , "memberOf" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(objectClass=user)";

			String searchBase = "OU=Docente Externo,OU=UNIOESTENET,DC=unioeste,DC=br";
			NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				// verificar antes de tudop se o usuário já está no banco de dados
				if (attrs.get("samaccountname") != null) {
					String samaccountname = attrs.get("samaccountname").get().toString();
					System.out.println(samaccountname);
					NamingEnumeration<?> grupos = attrs.get("memberof").getAll();
					while(grupos.hasMoreElements()){
						
						System.out.println(grupos.toString());
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (!ctx.equals(null))
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			;
		}
	}

	@Test
	void getImportarUsuarioDoLdap() {
		DirContext ctx = null;
		try {
			ctx = context();
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "cn", "samaccountname", "givenName", "department", "mail", "extensionattribute14",
					"distinguishedname", "extensionattribute15", "ipphone" };// , "memberOf" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(objectClass=user)";
			String[] base = { "OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Docente Externo,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Academico-Graduacao,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Academico-Mestrado,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Academico-Doutorado,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Academico-Pos-Doutorado,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Estagiario,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Cargo Comissionado,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Agentes Universitarios,OU=UNIOESTENET,DC=unioeste,DC=br",
					"OU=Academico-Especializacao,OU=UNIOESTENET,DC=unioeste,DC=br", };
			// String[] base = {"OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br"};
			int contador_usuarios = 0;
			Usuario usuario = null;
			for (String searchBase : base) {
				NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);
				String OUCategoria = (searchBase.split(",")[0]).split("=")[1];
				Categoria categoria = categoriaService.getCategoriaByNome(OUCategoria);
				if (categoria == null) {
					// criar categoria
					categoria = new Categoria();
					categoria.setNome(OUCategoria);
					categoriaService.save(categoria);
					categoria = categoriaService.getCategoriaByNome(OUCategoria);
				}

				while (answer.hasMoreElements()) {
					SearchResult sr = (SearchResult) answer.next();
					Attributes attrs = sr.getAttributes();
					// verificar antes de tudop se o usuário já está no banco de dados
					if (attrs.get("samaccountname") != null) {
						String samaccountname = attrs.get("samaccountname").get().toString();
						usuario = usuarioService.getUsuarioByName(samaccountname);
					}
					if (attrs != null && usuario == null) { // se o usuario estiver no banco de dados ... salta
						try {
							contador_usuarios++;
							String samaccountname = "";
							String cn = "";
							String givenname = "";
							String department = "";
							String mail = "";
							// String extensionattribute14 = "";
							// String distinguishedname = "";
							String celular = ""; // extensionattribute15
							String ramal = ""; // ipphone

							usuario = new Usuario();
							usuario.setLingua("pt_BR");
							if (attrs.get("samaccountname") != null) {
								samaccountname = attrs.get("samaccountname").get().toString();
								usuario.setNome(samaccountname);

							} else {
								System.out.println("samaccountname é vazio");
							}
							if (attrs.get("cn") != null) {
								cn = attrs.get("cn").get().toString();
								usuario.setNomeCompleto(cn);

							} else {
								System.out.println("cn é vazio");
							}

							if (attrs.get("givenname") != null) {
								givenname = attrs.get("givenname").get().toString();
								usuario.setPrimeiroNome(givenname);
							} else {
								System.out.println("givenname é vazio");
							}

							if (attrs.get("department") != null) {
								department = attrs.get("department").get().toString();
								// busca para ver se já existe a unidade que este usuário deve pertencer
								// no meu caso é CCET/CSC
								List<Unidade> unidades = unidadeService.getUnidadeByNome(department);
								Unidade unidade;
								if (unidades.size() > 0) {
									unidade = unidades.get(0);
								} else {
									// TODO lembrar de criar gatilho para opereção manual
									// pois é fácil criar a unidade mas tem que gerar o nome
									// ou a denominacaoAbreviadaInstitucional e também a localização
									// que aquela unidade possui e se tem um pai
									// por enquanto vamos acrescentar ao "nome" o departamento e a
									// cidade e preencher com a denominacaoAbreviadaInstitucional

									// cria a unidade
									unidade = new Unidade();
									// pela regra adotada no NTI a cidade é a última palavra
									String[] departArray = department.split("/");
									String cidade = departArray[departArray.length - 1]; // a última
									unidade.setCidade(cidade);
									// rever a questão do nome da unidade
									unidade.setNome(department);
									unidade.setDenominacaoAbreviadaInstitucional(department);
									unidadeService.save(unidade);
									// fazer a criação do evento de uma nova unidade criada
									Evento evento = new Evento();
									evento.setData(LocalDateTime.now());
									// evento.setUsuario(usuario);
									evento.setServico("Criação de uma nova Unidade -" + unidade.getCidade() + "-"
											+ department + "-" + department + "  pela inclusão do usuário:"
											+ samaccountname);

									eventoService.save(evento);
								}
								usuario.setUnidade(unidade);
							}

							// o perfil será geral usuário
							// depois por edição de um administrador poderá ser alterado

							List<Perfil> perfis = perfilService.getPerfilByNome("Usuário");
							Perfil perfil = null;
							if (perfis.size() > 0) {
								perfil = perfis.get(0);
							} else {
								perfil = new Perfil();
								perfil.setNome("Usuário");
							}

							perfilService.save(perfil);

							usuario.setPerfil(perfil);

							List<Email> listaEmails = new LinkedList<Email>();
							Email email1 = null, email2 = null;
							if (attrs.get("mail") != null) {
								mail = attrs.get("mail").get().toString();
								if (mail != null) {
									email1 = emailService.getEmailByEmail(mail);
									if (email1 == null) {
										email1 = new Email();
										email1.setEmail(mail);
									}
									email1.setUsuarioEmails(usuario);
									listaEmails.add(email1);
								}
							} else {
								System.out.println("mail é vazio");
							}

							if (attrs.get("extensionattribute14") != null) {
								mail = attrs.get("extensionattribute14").get().toString();
								if (mail != null && !mail.equals(email1.getEmail())) {
									email2 = emailService.getEmailByEmail(mail);
									if (email2 == null) {
										email2 = new Email();
										email2.setEmail(mail);
									}
									email2.setUsuarioEmails(usuario);
									listaEmails.add(email2);
								}
							} else {
								System.out.println("extensionattribute14 é vazio");
							}
							usuario.setEmails(listaEmails);

							// como o usuário está sendo criado não existe categoria ainda nele
							List<Categoria> listaCategoria = new LinkedList<Categoria>();
							listaCategoria.add(categoria);
							usuario.setCategorias(listaCategoria);

							usuario.setEstaAtivo(true);
							usuario.setExcluidoLdap(false);

							List<Telefone> listaTelefone = new LinkedList<Telefone>();
							if (attrs.get("extensionattribute15") != null) {
								celular = attrs.get("extensionattribute15").get().toString();
								if (celular != null) {
									Byte ddd = 0;
									String resto = celular.split(" ")[1];
									if (celular.split(" ")[0].length() <= 2) {
										ddd = Byte.parseByte(celular.split(" ")[0]);
									}
									String prefixo = resto.substring(0, 5);
									String sufixo = resto.substring(5);
									// busca se já existe no cadastro
									Telefone telefoneCelular = telefoneService.getTelefoneByNumero(ddd, prefixo,
											sufixo);
									if (telefoneCelular == null) {
										// criar objeto telefone
										telefoneCelular = new Telefone();
										telefoneCelular.setCodPais(Byte.parseByte("55"));
										telefoneCelular.setDdd(ddd);
										telefoneCelular.setPrefixo(prefixo);
										telefoneCelular.setSufixo(sufixo);
										telefoneCelular.setFixo(false);
										telefoneCelular.setRamal(false);
										telefoneService.save(telefoneCelular);
										// busca o ponteiro agora com o id do banco da categoria
										telefoneCelular = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
									}
									listaTelefone.add(telefoneCelular);
								}
							}

							if (attrs.get("ipphone") != null) {
								ramal = attrs.get("ipphone").get().toString();
								if (ramal != null) {
									Byte ddd = Byte.parseByte(ramal.substring(3, 5));
									String prefixo = ramal.substring(5, 9);
									String sufixo = ramal.substring(9);
									// busca se já existe no cadastro
									Telefone telefoneRamal = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
									if (telefoneRamal == null) {
										// cria ramal
										telefoneRamal = new Telefone();
										telefoneRamal.setCodPais(Byte.parseByte("55"));
										telefoneRamal.setDdd(ddd);
										telefoneRamal.setPrefixo(prefixo);
										telefoneRamal.setSufixo(sufixo);
										telefoneRamal.setFixo(false);
										telefoneRamal.setRamal(true);
										telefoneService.save(telefoneRamal);
										// busca o ponteiro agora com o id do banco da categoria
										telefoneRamal = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
									}
									// acrescenta na lista de telefones do usuario
									listaTelefone.add(telefoneRamal);
								}
							}

							if (listaTelefone.size() > 0)
								usuario.setTelefones(listaTelefone);

							usuario.setUltimaAlteracaoCadastro(LocalDateTime.now());

							// TODO verificar porque o último login ficou perdido ....tem que lembrar de
							// colocar nos eventos ocorridos
							usuario.setUltimoLogin(LocalDateTime.now());
							System.out.println(usuario.getNome());
							usuarioService.save(usuario);

							// buscar usuario do banco para poder relacionar
							// usuario = usuarioService.getUsuarioByName(samaccountname);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println("Total de usuários: " + contador_usuarios);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (!ctx.equals(null))
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			;
		}
	}

	/**
	 * This method will return Directory Context to the Called method,Used to bind
	 * with LDAP
	 */
	public DirContext context() throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		String ldapURL = "ldap://netsr-dmc05.unioeste.br:389";
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapURL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "UNIOESTENET\\glpi");
		env.put(Context.SECURITY_CREDENTIALS, "EuSouFeliz");
		DirContext ctx = new InitialLdapContext(env, null);
		return ctx;
	}
}
