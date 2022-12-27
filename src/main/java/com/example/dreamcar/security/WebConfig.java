package com.example.dreamcar.security;

import com.example.dreamcar.repositories.UserRepository;
import com.example.dreamcar.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(this.bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
//                .antMatchers("/employees").access("hasRole('ROLE_SYSTEM-ADMIN') or hasRole('ROLE_DATA-ADMIN') or hasRole('ROLE_USER')")
                .antMatchers("/login").permitAll()
                .antMatchers("/").access("hasRole('ROLE_SUPPLIER') or hasRole('ROLE_BUYER') or hasRole('ROLE_ADMIN')")
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/component");
    }
}
