package guru.sfg.brewery.config;

import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    @Primary
    PasswordEncoder sha256PasswordEncoder(){
        return new StandardPasswordEncoder();
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
                .password("ac65be86bd166abb6046b8b19a063d74ce1b92e444e8a65f89b633562d810f5b931eca90246cfc9d")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("ac65be86bd166abb6046b8b19a063d74ce1b92e444e8a65f89b633562d810f5b931eca90246cfc9d")
                .roles("ADMIN")
                .and()
                .withUser("spring")
                .password("fdd03b9cef23798dec77c67db4c9453b713cecabaa02fb0c51fe176d4c9bcac1ec614df409eb34c9")
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("d5e42acee7520dd413ac5f4b930a8f8985f289612484153929b39d9083561495b207d095aa1b6747")
                .roles("CUSTOMER");
    }
}
