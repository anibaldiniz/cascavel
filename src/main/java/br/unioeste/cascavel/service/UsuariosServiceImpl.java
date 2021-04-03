package br.unioeste.cascavel.service;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.repository.UsuarioRepository;


@Service
public class UsuariosServiceImpl implements UsuariosService {

	private static List<Usuario> usuarios;  // observar a questão do Bean do usuário, como é estático 
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
    @Override
    public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
    }
	
    @Override
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);

    }

    @Override
    public Usuario getUsuarioById(long id) {
        Optional<Usuario> opcional = usuarioRepository.findById(id);
        Usuario usuario = null;
        if (opcional.isPresent()) {
            usuario = opcional.get();
        } else {
            throw new RuntimeException("Usuario não encontrado");
        }
        return usuario;
    }

    @Override
    public void apagarUsuarioById(long id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario getUsuarioByName(String nome) {
        return usuarioRepository.findByName(nome);
    }

    @Override
    public void deleteAll() {
        usuarioRepository.deleteAll();
        
    }



    public Page<Usuario> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> list;
		if (usuarios == null || usuarios.isEmpty())
			usuarios = usuarioRepository.findAll();
        if (usuarios.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usuarios.size());
            list = usuarios.subList(startItem, toIndex);
        }

        Page<Usuario> UsuarioPage
          = new PageImpl<Usuario>(list, PageRequest.of(currentPage, pageSize), usuarios.size());

        return UsuarioPage;
    }

    // @Override
    // public Boolean importarUsuariosLdap() {
    //     DirContext ctx = null;
	// 	try {
	// 		ctx = context();
	// 		SearchControls searchCtls = new SearchControls();
	// 		String returnedAtts[] = { "cn", "samaccountname", "givenName", "department", "mail", "extensionattribute14",
	// 				"distinguishedname", "extensionattribute15", "ipphone" };// , "memberOf" };
	// 		searchCtls.setReturningAttributes(returnedAtts);
	// 		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	// 		String searchFilter = "(objectClass=user)";
	// 		String[] base = { "OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Docente Externo,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Academico-Graduacao,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Academico-Mestrado,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Academico-Doutorado,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Academico-Pos-Doutorado,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Estagiario,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Cargo Comissionado,OU=UNIOESTENET,DC=unioeste,DC=br",
	// 				"OU=Agentes Universitarios,OU=UNIOESTENET,DC=unioeste,DC=br" };
	// 		// String[] base = {"OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br"};
	// 		int contador_usuarios = 0;
	// 		Usuario usuario = null;
	// 		for (String searchBase : base) {
	// 			NamingEnumeration<?> answer = ctx.search(searchBase, searchFilter, searchCtls);
	// 			String OUCategoria = (searchBase.split(",")[0]).split("=")[1];
	// 			Categoria categoria = categoriaService.getCategoriaByNome(OUCategoria);
	// 			if (categoria == null) {
	// 				// criar categoria
	// 				categoria = new Categoria();
	// 				categoria.setNome(OUCategoria);
	// 				categoriaService.save(categoria);
	// 				categoria = categoriaService.getCategoriaByNome(OUCategoria);
	// 			}

	// 			while (answer.hasMoreElements()) {
	// 				SearchResult sr = (SearchResult) answer.next();
	// 				Attributes attrs = sr.getAttributes();
	// 				// verificar antes de tudop se o usuário já está no banco de dados
	// 				if (attrs.get("samaccountname") != null) {
	// 					String samaccountname = attrs.get("samaccountname").get().toString();
	// 					usuario = usuarioService.getUsuarioByName(samaccountname);
	// 				}
	// 				if (attrs != null && usuario == null) { // se o usuario estiver no banco de dados ... salta
	// 					try {
	// 						contador_usuarios++;
	// 						String samaccountname = "";
	// 						String cn = "";
	// 						String givenname = "";
	// 						String department = "";
	// 						String mail = "";
	// 						//String extensionattribute14 = "";
	// 						//String distinguishedname = "";
	// 						String celular = ""; // extensionattribute15
	// 						String ramal = ""; // ipphone

	// 						usuario = new Usuario();
	// 						usuario.setLingua("pt_BR");
	// 						if (attrs.get("samaccountname") != null) {
	// 							samaccountname = attrs.get("samaccountname").get().toString();
	// 							usuario.setNome(samaccountname);

	// 						} else {
	// 							System.out.println("samaccountname é vazio");
	// 						}
	// 						if (attrs.get("cn") != null) {
	// 							cn = attrs.get("cn").get().toString();
	// 							usuario.setNomeCompleto(cn);

	// 						} else {
	// 							System.out.println("cn é vazio");
	// 						}

	// 						if (attrs.get("givenname") != null) {
	// 							givenname = attrs.get("givenname").get().toString();
	// 							usuario.setPrimeiroNome(givenname);
	// 						} else {
	// 							System.out.println("givenname é vazio");
	// 						}

	// 						if (attrs.get("department") != null) {
	// 							department = attrs.get("department").get().toString();
	// 							// busca para ver se já existe a unidade que este usuário deve pertencer
	// 							// no meu caso é CCET/CSC
	// 							List<Unidade> unidades = unidadeService.getUnidadeByNome(department);
	// 							Unidade unidade;
	// 							if (unidades.size() > 0) {
	// 								unidade = unidades.get(0);
	// 							} else {
	// 								// TODO lembrar de criar gatilho para opereção manual
	// 								// pois é fácil criar a unidade mas tem que gerar o nome
	// 								// ou a denominacaoAbreviadaInstitucional e também a localização
	// 								// que aquela unidade possui e se tem um pai
	// 								// por enquanto vamos acrescentar ao "nome" o departamento e a
	// 								// cidade e preencher com a denominacaoAbreviadaInstitucional

	// 								// cria a unidade
	// 								unidade = new Unidade();
	// 								// pela regra adotada no NTI a cidade é a última palavra
	// 								String[] departArray = department.split("/");
	// 								String cidade = departArray[departArray.length - 1]; // a última
	// 								unidade.setCidade(cidade);
	// 								// rever a questão do nome da unidade
	// 								unidade.setNome(department);
	// 								unidade.setDenominacaoAbreviadaInstitucional(department);
	// 								unidadeService.save(unidade);
	// 								// fazer a criação do evento de uma nova unidade criada
	// 								// Evento evento = new Evento();
	// 								// evento.setData(LocalDateTime.now());
	// 								// // evento.setUsuario(usuario);
	// 								// evento.setServico("Criação de uma nova Unidade -" + unidade.getCidade() + "-"
	// 								// 		+ department + "-" + department + "  pela inclusão do usuário:"
	// 								// 		+ samaccountname);

	// 								// eventoService.save(evento);
	// 							}
	// 							usuario.setUnidade(unidade);
	// 						} else {
	// 							System.out.println("department é vazio");
	// 						}

	// 						// o perfil será geral usuário
	// 						// depois por edição de um administrador poderá ser alterado

	// 						List<Perfil> perfis = perfilService.getPerfilByNome("Usuário");
	// 						Perfil perfil = null;
	// 						if (perfis.size() > 0) {
	// 							perfil = perfis.get(0);
	// 						} else {
	// 							perfil = new Perfil();
	// 							perfil.setNome("Usuário");
	// 						}

	// 						perfilService.save(perfil);

	// 						usuario.setPerfil(perfil);

	// 						List<Email> listaEmails = new LinkedList<Email>();
	// 						Email email1 = null, email2 = null;
	// 						if (attrs.get("mail") != null) {
	// 							mail = attrs.get("mail").get().toString();
	// 							if (mail != null) {
	// 								email1 = emailService.getEmailByEmail(mail);
	// 								if (email1 == null) {
	// 									email1 = new Email();
	// 									email1.setEmail(mail);
	// 								}
	// 								email1.setUsuarioEmails(usuario);
	// 								listaEmails.add(email1);
	// 							}
	// 						} else {
	// 							System.out.println("mail é vazio");
	// 						}

	// 						if (attrs.get("extensionattribute14") != null) {
	// 							mail = attrs.get("extensionattribute14").get().toString();
	// 							if (mail != null && !mail.equals(email1.getEmail())) {
	// 								email2 = emailService.getEmailByEmail(mail);
	// 								if (email2 == null) {
	// 									email2 = new Email();
	// 									email2.setEmail(mail);
	// 								}
	// 								email2.setUsuarioEmails(usuario);
	// 								listaEmails.add(email2);
	// 							}
	// 						} else {
	// 							System.out.println("extensionattribute14 é vazio");
	// 						}
	// 						usuario.setEmails(listaEmails);

	// 						// para melhorar a categoria vou trocar o codigo abaixo, pelo filtro do AD, pois
	// 						// parece mais racional
	// 						// if (attrs.get("distinguishedname") != null) {
	// 						// distinguishedname = attrs.get("distinguishedname").get().toString();
	// 						// String OUCategoria = (distinguishedname.split(",")[1]).split("=")[1];
	// 						// Categoria categoria = categoriaService.getCategoriaByNome(OUCategoria);
	// 						// if (categoria == null) {
	// 						// // criar categoria
	// 						// categoria = new Categoria();
	// 						// categoria.setNome(OUCategoria);
	// 						// categoriaService.save(categoria);
	// 						// categoria = categoriaService.getCategoriaByNome(OUCategoria);
	// 						// }
	// 						// // como o usuário está sendo criado não existe categoria ainda nele
	// 						// List<Categoria> listaCategoria = new LinkedList<Categoria>();
	// 						// listaCategoria.add(categoria);
	// 						// usuario.setCategorias(listaCategoria);
	// 						// } else {
	// 						// System.out.println("distinguishedname é vazio");
	// 						// }

	// 						// como o usuário está sendo criado não existe categoria ainda nele
	// 						List<Categoria> listaCategoria = new LinkedList<Categoria>();
	// 						listaCategoria.add(categoria);
	// 						usuario.setCategorias(listaCategoria);

	// 						usuario.setEstaAtivo(true);
	// 						usuario.setExcluidoLdap(false);

	// 						List<Telefone> listaTelefone = new LinkedList<Telefone>();
	// 						if (attrs.get("extensionattribute15") != null) {
	// 							celular = attrs.get("extensionattribute15").get().toString();
	// 							if (celular != null) {
	// 								Byte ddd = 0;
	// 								String resto = celular.split(" ")[1];
	// 								if (celular.split(" ")[0].length() <= 2) {
	// 									ddd = Byte.parseByte(celular.split(" ")[0]);
	// 								}
	// 								String prefixo = resto.substring(0, 5);
	// 								String sufixo = resto.substring(5);
	// 								// busca se já existe no cadastro
	// 								Telefone telefoneCelular = telefoneService.getTelefoneByNumero(ddd, prefixo,
	// 										sufixo);
	// 								if (telefoneCelular == null) {
	// 									// criar objeto telefone
	// 									telefoneCelular = new Telefone();
	// 									telefoneCelular.setCodPais(Byte.parseByte("55"));
	// 									telefoneCelular.setDdd(ddd);
	// 									telefoneCelular.setPrefixo(prefixo);
	// 									telefoneCelular.setSufixo(sufixo);
	// 									telefoneCelular.setFixo(false);
	// 									telefoneCelular.setRamal(false);
	// 									telefoneService.save(telefoneCelular);
	// 									// busca o ponteiro agora com o id do banco da categoria
	// 									telefoneCelular = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
	// 								}
	// 								listaTelefone.add(telefoneCelular);
	// 							}
	// 						} else {
	// 							System.out.println("extensionattribute15 é vazio");
	// 						}

	// 						if (attrs.get("ipphone") != null) {
	// 							ramal = attrs.get("ipphone").get().toString();
	// 							if (ramal != null) {
	// 								Byte ddd = Byte.parseByte(ramal.substring(3, 5));
	// 								String prefixo = ramal.substring(5, 9);
	// 								String sufixo = ramal.substring(9);
	// 								// busca se já existe no cadastro
	// 								Telefone telefoneRamal = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
	// 								if (telefoneRamal == null) {
	// 									// cria ramal
	// 									telefoneRamal = new Telefone();
	// 									telefoneRamal.setCodPais(Byte.parseByte("55"));
	// 									telefoneRamal.setDdd(ddd);
	// 									telefoneRamal.setPrefixo(prefixo);
	// 									telefoneRamal.setSufixo(sufixo);
	// 									telefoneRamal.setFixo(false);
	// 									telefoneRamal.setRamal(true);
	// 									telefoneService.save(telefoneRamal);
	// 									// busca o ponteiro agora com o id do banco da categoria
	// 									telefoneRamal = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
	// 								}
	// 								// acrescenta na lista de telefones do usuario
	// 								listaTelefone.add(telefoneRamal);
	// 							}
	// 						} else {
	// 							System.out.println("ipphone é vazio");
	// 						}

	// 						if (listaTelefone.size() > 0)
	// 							usuario.setTelefones(listaTelefone);

	// 						usuario.setUltimaAlteracaoCadastro(LocalDateTime.now());

	// 						// TODO verificar porque o último login ficou perdido ....tem que lembrar de
	// 						// colocar nos eventos ocorridos
	// 						usuario.setUltimoLogin(LocalDateTime.now());
	// 						usuarioService.save(usuario);

	// 					} catch (NullPointerException e) {
	// 						System.out.println("erro!!!" + e.getMessage());
	// 					}
	// 				}
	// 			}
	// 		}
	// 		System.out.println("Total de usuários: " + contador_usuarios);
	// 	} catch (Exception e) {
	// 		System.out.println(e.getMessage());
    //         return false;
	// 	} finally {
	// 		if (!ctx.equals(null))
    //             try {
    //                 ctx.close();
    //             } catch (NamingException e) {
    //                 e.printStackTrace();
    //                 return false;
    //             }
	// 	}
    //     return true;
    // }


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

	@Override
	public List<Usuario> getUsuarioByNameLike(String nome) {
		
		return usuarioRepository.findByNomeLike(nome);
	}

    
    
}
