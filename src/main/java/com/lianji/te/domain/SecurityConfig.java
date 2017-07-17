package com.lianji.te.domain;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/public/**").permitAll()
//                .antMatchers("/users/**").hasAuthority("ADMIN")
//                .anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .deleteCookies("remember-me")
//                .logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//                .rememberMe();
//    }
}
