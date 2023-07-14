package com.namsu.inventoryspring.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // url 접근 권한
                .authorizeRequests()
                .antMatchers("/join-form").permitAll()
                .antMatchers("/join").permitAll()
                .antMatchers("/login-form").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
//                .anyRequest().permitAll()

                // h2db console 엑박 방지
                .and().headers()
                .frameOptions()
                .sameOrigin()

                // 자사 로그인
                .and()
                .formLogin()
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")

                // 로그아웃
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-form")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
