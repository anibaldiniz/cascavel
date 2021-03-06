package br.unioeste.cascavel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import br.unioeste.cascavel.model.Usuario;
import br.unioeste.cascavel.service.PerfisService;
import br.unioeste.cascavel.service.UsuariosService;


public class CustomUserMapper extends LdapUserDetailsMapper {

    @Autowired
    UsuariosService usuarioService;

    @Autowired
    PerfisService perfilService;


@Override
public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities){

    UserDetails details = super.mapUserFromContext(ctx, username, authorities);
   
   
    // a lógica é:
    //      usuário logado está no banco?
    //            se sim
    //                 verifica "changedValues"
    //                     se algum mudou .... altera no banco ... os dados que interessam
    //            não está no banco
    //                     inclui o usuário
   
    Usuario usuario = usuarioService.getUsuarioByName(username);
    if(usuario != null){  //usuário está no banco, já logou uma vez no ldap
        String[] changedValues = ctx.getStringAttributes("whenchanged");
        if(changedValues.length>0){ //se os dados foram mudados
            //faz as alteraçoes necessárias se a data do AD for menor que a data da ultima modificacao no banco
        }
    } else {
        usuario = new Usuario();
        usuario.setNome(username);
        usuario.setLingua("pt_BR");
        usuario.setNomeCompleto(ctx.getStringAttributes("cn")[0]);
        usuario.setPrimeiroNome(ctx.getStringAttributes("givenname")[0]);
        //TODO fazer busca das listas que o usuário possui para ver a qual delas ele pertence
        // ?????????
        usuario.setCategorias(null);
        //TODO dos dados que vem ... tentar extrair a localização...
        //principalmente a unidade
        // ????????
        usuario.setLocalizacoes(null);
        // o perfil será geral usuário
        //depois por edição de um administrador poderá ser alterado
        usuario.setPerfil(perfilService.getPerfilByNome("usuário"));

        //TODO pesquisar o campo do lDap que tras os emails
        usuario.setEmails(null);
        usuario.setEstaAtivo(true);
        usuario.setExcluidoLdap(false);

        //TODO pesquisar os grupos que ele pode pertencer
        usuario.setGrupos(null);
        //TODO tenho que ver melhor o que pensei para este campo
        usuario.setResponsaveis(null);
        //TODO pesquisar os telefones, incluir os telefone e acrescentar em uma lista que será passada aqui
        usuario.setTelefones(null);
        usuario.setUltimaAlteracaoCadastro(LocalDateTime.now());

        //TODO verificar porque o último login ficou perdido  ....tem que lembrar de colocar nos eventos ocorridos
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioService.save(usuario);
    }

    /*
        categorias      
            tirado das listas abaixo:
                Lista de Emails-Academico-Doutorado-CCsc
                Lista de Emails-Academico-Especializacao-CCsc
                Lista de Emails-Academico-Graduacao-CCsc
                Lista de Emails-Academico-Mestrado-CCsc
                Lista de Emails-Academico-Pos-Doutorado-CCsc
                Lista de Emails-Docente-CCsc
                Lista de Emails-Agente Universitario-CCsc   lista.agente-universitario.ccsc  OU=grupos
                Lista de Emails-Cargo Comissionado-CCsc
                Lista de Emails-Estagiario-CCsc

            Docente  - lista.docente-ccsc
            
            Discente - lista.academico-doutorado.ccsc
                       lista.academico-especializacao.ccsc
                       lista.academico-graduacao.ccsc
                       lista.academico-mestrado.ccsc
                       lista.academico-pos-doutorado.ccsc

            Agente Universitário - lista.agente-universitario.ccsc
                       
            Cargo Comissionado - lista.cargo-comissionado.ccsc

            Estagiário - lista.estagiario.ccsc

            Externo
            
        perfis
			Administrador
            chefe de seção
            corresponsável seção
			usuario

        grupos 
            seção recursos humanos
            seção patrimônio
            seção almoxarifado
            seção acessoria jurídica
            seção informática
            seção financeiro
            seção contabilidade
            seção compras
            seção licitação
            secretaria administrativa
            secretaria financeira

            direção geral


    */
   
    System.out.println("passou por aqui "+ username);                                           //username éo nome do usuário anibal.diniz
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("givenname")));             //primeiro nome
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("cn")));                    //nome completo
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("distinguishedname")));     //dados: [CN=Anibal Mantovani Diniz,OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br]

    System.out.println( Arrays.deepToString(ctx.getStringAttributes("memberof")));              //todos os grupos
    return details;        
}

}