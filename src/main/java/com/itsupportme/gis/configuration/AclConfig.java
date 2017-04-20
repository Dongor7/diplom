package com.itsupportme.gis.configuration;

import com.itsupportme.gis.component.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AclConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/login/**").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/**").access(RoleRepository.hasRole(RoleRepository.ROLE_USER))
                .and()
                    .formLogin().loginPage("/login").defaultSuccessUrl("/login/success", true)
                    .failureUrl("/login/error")
                    .usernameParameter("username").passwordParameter("password")
                .and()
                    .logout().logoutSuccessUrl("/login?logout")
                .and()
                    .csrf()
                .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/login")
            ;
        }
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/api/**")
                        .csrf().disable()
                        .authorizeRequests()
                        .anyRequest().access(RoleRepository.hasRole(RoleRepository.ROLE_API))
                    .and()
                        .httpBasic()
                    .and()
                         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
        }
    }
}
