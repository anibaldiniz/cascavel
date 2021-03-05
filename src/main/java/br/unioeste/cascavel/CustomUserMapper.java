package br.unioeste.cascavel;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;


public class CustomUserMapper extends LdapUserDetailsMapper {

//   @Override
//   public CustomUserDetails mapUserFromContext(DirContextOperations ctx, String username,
//       Collection<? extends GrantedAuthority> authorities) {

//     // set from userDetails
//     UserDetails details = super.mapUserFromContext(ctx, username, authorities);

//     // set directly from ctx
//     CustomUserDetails customUserDetails = new CustomUserDetails();
//     customUserDetails.setFirstName(ctx.getStringAttribute("givenName"));
//     customUserDetails.setLastName(ctx.getStringAttribute("sn"));

//     return customUserDetails;
//   }

@Override
public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities){

    UserDetails details = super.mapUserFromContext(ctx, username, authorities);
    String[] changedValues = ctx.getStringAttributes("whenchanged");
   
    // a lógica é:
    //      usuário logado está no banco?
    //            se sim
    //                 verifica "changedValues"
    //                     se algum mudou .... altera no banco ... os dados que interessam
    //            não está no banco
    //                     inclui o usuário

    
   
    System.out.println("passou por aqui "+ username);                                           //username éo nome do usuário anibal.diniz
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("givenname")));             //primeiro nome
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("cn")));                    //nome completo
    System.out.println( Arrays.deepToString(ctx.getStringAttributes("distinguishedname")));     //dados: [CN=Anibal Mantovani Diniz,OU=Docente,OU=UNIOESTENET,DC=unioeste,DC=br]

    System.out.println( Arrays.deepToString(ctx.getStringAttributes("memberof")));              //todos os grupos
    return details;        
}

}