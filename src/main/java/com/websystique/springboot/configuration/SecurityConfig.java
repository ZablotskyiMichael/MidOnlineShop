package com.websystique.springboot.configuration;

import com.websystique.springboot.filters.CustomFilter;
import com.websystique.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
//@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select name as username, password, enabled from APP_USER where name=?")
                .authoritiesByUsernameQuery("select app_user.NAME as username,  dictionary.name from app_user \n" +
                        " join dictionary on app_user.role_id=dictionary.dictionary_id where dictionary.discriminator='ROLES' && app_user.name =?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                new CustomFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/registration/**","/#/registration/**","/user/registration").permitAll()

                .antMatchers("/#/administrator/**","/administrator/**").hasRole("ADMIN")
                .antMatchers("/admin/**","/product/**","/partials/storage","/partials/category","partials/properties","/#/storage","/#/properties").hasAnyRole("ADMIN")
                .antMatchers("/partials/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/user/**","/storage/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/#/user")
                .and()
                .httpBasic()
                .and().csrf().disable()
               // .formLogin().disable()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/secError");
        http.exceptionHandling().accessDeniedPage("/secError");
    }

}
