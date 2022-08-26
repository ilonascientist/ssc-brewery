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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Primary
    PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
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
                .password("$2a$10$yBE2oFHVSQM7SkSEwztobembs/A4rNTZjrAKFRQZNC8gk.8Yh7.QK")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("$2a$10$yBE2oFHVSQM7SkSEwztobembs/A4rNTZjrAKFRQZNC8gk.8Yh7.QK")
                .roles("ADMIN")
                .and()
                .withUser("spring")
                .password("$2a$10$u0agNec9d.cWWzeIO2w/Z.oGfWrlNd/75QI.WIpL35o.TULsjRHdK")
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("scott")
                .password("$2a$10$TLv0ZJnHt5G/mZKjiK/xq.whClHgn0Yf/BnCv1wF5FPsJDaCvTXne")
                .roles("CUSTOMER");
    }
}
