package br.unioeste.cascavel;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private Environment env;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // .antMatchers("/swagger-ui.html","/v2/api-docs", "/").permitAll()
        .anyRequest()// .permitAll();
        .authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
        .logoutSuccessUrl("/login.html").permitAll();

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
    }
    return new CustomUserMapper();
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

  
}