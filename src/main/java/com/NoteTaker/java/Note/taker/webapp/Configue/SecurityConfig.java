package com.NoteTaker.java.Note.taker.webapp.Configue;

import com.NoteTaker.java.Note.taker.webapp.Service.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String[] ALLOWED_URLS = {
            "/h2-console/**"
    };

    @Autowired
    public Oauth2Service oauth2Service;

    @Bean
    public customUserDetailService getCustomUserDetailService() {
        return new customUserDetailService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf().
                disable().
                authorizeHttpRequests().
                antMatchers("/**", "/oauth2/**").
                permitAll().
                and().
                formLogin().
                loginPage("/login").
                loginProcessingUrl("/login").
                defaultSuccessUrl("/addNotes").
                and().
                oauth2Login().
                loginPage("/login").
                userInfoEndpoint().
                userService(oauth2Service).and().and();
        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(getCustomUserDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(ALLOWED_URLS);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
