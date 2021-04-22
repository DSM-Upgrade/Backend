package com.dsmupgrade.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .formLogin().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/auth/**", "/fields").permitAll()
                    .antMatchers("/authority/**").hasAuthority(Authority.ADMIN.name())
                    .antMatchers(HttpMethod.POST, "/notification/notice", "/notification/vote").hasAuthority(Authority.NOTICE_MANAGER.name())
                    .antMatchers(HttpMethod.PATCH, "/notification/notice/*", "/notification/vote/*").hasAuthority(Authority.NOTICE_MANAGER.name())
                    .antMatchers(HttpMethod.DELETE, "/notification/*").hasAuthority(Authority.NOTICE_MANAGER.name())
                    .anyRequest().authenticated().and()
                .apply(new JwtConfigurer(jwtTokenProvider)).and()
                .apply(new ExceptionConfigurer()).and()
                .apply(new CorsConfigurer());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
