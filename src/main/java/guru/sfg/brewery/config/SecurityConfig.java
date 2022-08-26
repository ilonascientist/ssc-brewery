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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder noOpPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Primary
    PasswordEncoder ldapPasswordEncoder(){
        return new LdapShaPasswordEncoder();
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
                .password("{SSHA}Fx82TXmvL1r5Dtd00AzZ9X2RiUFMyQMt4GWTOA==")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{SSHA}Fx82TXmvL1r5Dtd00AzZ9X2RiUFMyQMt4GWTOA==")
                .roles("ADMIN")
                .and()
                .withUser("spring")
                .password("{SSHA}NSQkB2CSMDDmI3EJ16sdy5+PFuDi1ogPD2kHnA==")
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("{SSHA}r0gw4AVSSLjVuTGVeKVtvf/UMYvJPeJwoOa2Ww==")
                .roles("CUSTOMER");
    }
}
