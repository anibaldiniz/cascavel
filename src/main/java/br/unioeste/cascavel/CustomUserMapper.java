package br.unioeste.cascavel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

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

public class CustomUserMapper extends LdapUserDetailsMapper {

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

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {

        UserDetails details = super.mapUserFromContext(ctx, username, authorities);

        // a lógica é:
        // só chega aqui se o usuário está logado
        // usuário logado está no banco?
        // se sim
        // verifica "changedValues"
        // se algum mudou .... altera no banco ... os dados que interessam
        // não está no banco
        // inclui o usuário

        Usuario usuario = usuarioService.getUsuarioByName(username);

        if (usuario != null) { // usuário está no banco, já logou uma vez no ldap
            String[] changedValues = ctx.getStringAttributes("whenchanged");
            if (changedValues.length > 0) { // se os dados foram mudados
                // faz as alteraçoes necessárias se a data do AD for menor que a data da
                // ultima
                // // modificacao no banco
            }
        } else {
            usuario = new Usuario();
            usuario.setNome(username);
            usuario.setLingua("pt_BR");
            usuario.setNomeCompleto(ctx.getStringAttributes("cn")[0]);
            usuario.setPrimeiroNome(ctx.getStringAttributes("givenname")[0]);
            // através do atributo do AD "department" se pode obter o nome abreviado do
            // local onde o usuário está lotado e a cidade
            String departamento = (ctx.getStringAttributes("department"))[0];

            // busca para ver se já existe a unidade que este usuário deve pertencer
            // no meu caso é CCET/CSC
            List<Unidade> unidades = unidadeService.getUnidadeByNome(departamento);
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
                String[] departArray = departamento.split("/");
                String cidade = departArray[departArray.length - 1]; // a última
                unidade.setCidade(cidade);
                // rever a questão do nome da unidade
                unidade.setNome(departamento);
                unidade.setDenominacaoAbreviadaInstitucional(departamento);
            }
            unidadeService.save(unidade);

            usuario.setUnidade(unidade);
            // fazer a criação do evento de uma nova unidade criada
            Evento evento = new Evento();
            evento.setData(LocalDateTime.now());
            evento.setServico("Criação de uma nova Unidade -" + unidade.getCidade() + "-" + departamento + "-"
                    + departamento + "  pela inclusão do usuário:" + username);
            eventoService.save(evento);

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

            Email email1 = null, email2 = null;
            String email = ctx.getStringAttributes("mail")[0];
            if (email != null) {
                email1 = emailService.getEmailByEmail(email);
                if (email1 == null) {
                    email1 = new Email();
                    email1.setEmail(email);
                }
                //emailService.save(email1);
                email1.setUsuarioEmails(usuario);
            }

            // em princípio todos dos usuários tem este campo preenchido pela regra do AD ..
            // segundo email
            email = ctx.getStringAttributes("extensionattribute14")[0];
            if (email != null) {
                email2 = emailService.getEmailByEmail(email);
                if (email2 == null) {
                    email2 = new Email();
                    email2.setEmail(email);
                }
                //emailService.save(email2);
                email2.setUsuarioEmails(usuario);
                
            }
            List<Email> listaEmails = new LinkedList<Email>();
            listaEmails.add(email1);
            listaEmails.add(email2);
            usuario.setEmails(listaEmails);

            // lá no final acrescenta ao usuário estes objeto

            String distinguishedname = ctx.getStringAttributes("distinguishedname")[0];
            String OUCategoria = (distinguishedname.split(",")[1]).split("=")[1];
            Categoria categoria = categoriaService.getCategoriaByNome(OUCategoria);
            if (categoria == null) {
                // criar categoria
                categoria = new Categoria();
                categoria.setNome(OUCategoria);
                categoriaService.save(categoria);
                categoria = categoriaService.getCategoriaByNome(OUCategoria);
                // busca o ponteiro agora com o id do banco da categoria
                // categoria = categoriaService.getCategoriaByNome(OUCategoria);
                // criar um evento da criação de uma nova categoria
                evento = new Evento();
                evento.setData(LocalDateTime.now());
                evento.setServico(
                        "Criação de uma nova Cateforia - " + OUCategoria + "  pela inclusão do usuário:" + username);
                eventoService.save(evento);
            }
            // como o usuário está sendo criado não existe categoria ainda nele
            List<Categoria> listaCategoria = new LinkedList<Categoria>();
            listaCategoria.add(categoria);
            usuario.setCategorias(listaCategoria);

            // TODO dos dados que vem ... tentar extrair a localização...
            //
            // ????????
            // usuario.setLocalizacoes(null);

            usuario.setEstaAtivo(true);
            usuario.setExcluidoLdap(false);

            // TODO pesquisar os grupos que ele pode pertencer (aind será manual)
            // usuario.setGrupos(null);

            // TODO tenho que ver melhor o que pensei para este campo
            // usuario.setResponsaveis(null);

            // pegar os telefones, celular e ramal na sequencia
            String celular = ctx.getStringAttributes("extensionattribute15")[0];
            // 45 991070916
            // crfia uma lista para conter os telefones cadastrados do usuário
            List<Telefone> listaTelefone = new LinkedList<Telefone>();
            if (celular != null) {
                String resto = celular.split(" ")[1];
                Byte ddd = Byte.parseByte(celular.split(" ")[0]);
                String prefixo = resto.substring(0, 5);
                String sufixo = resto.substring(5);
                // busca se já existe no cadastro
                Telefone telefoneCelular = telefoneService.getTelefoneByNumero(ddd, prefixo, sufixo);
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

            // para pegar o telefone que é do usuário - ramal
            String ramal = ctx.getStringAttributes("ipphone")[0];
            // *004532204271
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
            if (listaTelefone.size() > 0)
                usuario.setTelefones(listaTelefone);

            usuario.setUltimaAlteracaoCadastro(LocalDateTime.now());

            // TODO verificar porque o último login ficou perdido ....tem que lembrar de
            // colocar nos eventos ocorridos
            usuario.setUltimoLogin(LocalDateTime.now());
            usuarioService.save(usuario);
            
            // buscar usuario do banco para poder relacionar
            //usuario = usuarioService.getUsuarioByName(username);

            /*
             * categorias tirado das listas abaixo: Lista de Emails-Academico-Doutorado-CCsc
             * Lista de Emails-Academico-Especializacao-CCsc Lista de
             * Emails-Academico-Graduacao-CCsc Lista de Emails-Academico-Mestrado-CCsc Lista
             * de Emails-Academico-Pos-Doutorado-CCsc Lista de Emails-Docente-CCsc Lista de
             * Emails-Agente Universitario-CCsc lista.agente-universitario.ccsc OU=grupos
             * Lista de Emails-Cargo Comissionado-CCsc Lista de Emails-Estagiario-CCsc
             * 
             * Docente - lista.docente-ccsc
             * 
             * Discente - lista.academico-doutorado.ccsc lista.academico-especializacao.ccsc
             * lista.academico-graduacao.ccsc lista.academico-mestrado.ccsc
             * lista.academico-pos-doutorado.ccsc
             * 
             * Agente Universitário - lista.agente-universitario.ccsc
             * 
             * Cargo Comissionado - lista.cargo-comissionado.ccsc
             * 
             * Estagiário - lista.estagiario.ccsc
             * 
             * Externo
             * 
             * perfis Administrador chefe de seção corresponsável seção usuario
             * 
             * grupos seção recursos humanos seção patrimônio seção almoxarifado seção
             * acessoria jurídica seção informática seção financeiro seção contabilidade
             * seção compras seção licitação
             * 
             * secretaria administrativa secretaria financeira secretaria academica
             * 
             * direção geral
             * 
             * 
             */

        }
        return details;
    }
}
