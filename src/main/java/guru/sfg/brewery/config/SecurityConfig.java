package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder noOpPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    PasswordEncoder ldapPasswordEncoder(){
        return new LdapShaPasswordEncoder();
    }

    @Bean
    PasswordEncoder sha256PasswordEncoder(){
        return new StandardPasswordEncoder();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    PasswordEncoder delegatingPasswordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Primary
    PasswordEncoder sfgDelegatingPasswordEncoder(){
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(
                        auth -> auth
                                .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                                .antMatchers("/beers/find", "/beers*").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()
                )
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Fluent API
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{bcrypt}$2a$10$yBE2oFHVSQM7SkSEwztobembs/A4rNTZjrAKFRQZNC8gk.8Yh7.QK")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{bcrypt}$2a$10$yBE2oFHVSQM7SkSEwztobembs/A4rNTZjrAKFRQZNC8gk.8Yh7.QK")
                .roles("ADMIN")
                .and()
                .withUser("spring")
                .password("{ldap}{SSHA}Pi3sbRoa6dBvXE76+vY3T/Lb6WnvGITxzf/v7Q==")
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("{bcrypt15}$2b$15$1HpBoTDmPxrhe4zdUlaWoOeNDeq05MnuypQRrRvkgwzTHzELYqg76")
                .roles("CUSTOMER");
    }
}
