package br.unioeste.cascavel;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/swagger-ui.html", "/").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").permitAll()
        .and()
        .logout().permitAll();
    
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
    authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
        .userDetailsService(userDetailsService());
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
  }

  @Bean
  public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
    ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("unioeste.br",
        "ldap://netsr-dmc05.unioeste.br:389/", "dc=unioeste,dc=br");
    provider.setConvertSubErrorCodesToExceptions(true);
    provider.setUseAuthenticationRequestCredentials(true);
    provider.setUserDetailsContextMapper(userDetailsContextMapper());
    return provider;
  }

  @Bean
  public UserDetailsContextMapper userDetailsContextMapper() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {

      Object principal = userDetailsService().loadUserByUsername(authentication.getName());
      System.out.println("user" + principal);
    }
    return new CustomUserMapper();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
      web
      .ignoring()
      .antMatchers("/v2/api-docs",
                                 "/configuration/ui",
                                 "/swagger-resources/**",
                                 "/configuration/security",
                                 "/swagger-ui.html",
                                 "/swagger-ui/**",
                                 "/webjars/**");
  }

}