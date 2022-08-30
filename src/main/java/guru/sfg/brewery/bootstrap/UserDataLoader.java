package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }

    private void loadSecurityData() {
        Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customerRole = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());

        userRepository.save(User.builder()
                .username("spring")
                .password(encoder.encode("test"))
                .authority(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .authority(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(encoder.encode("tiger"))
                .authority(customerRole)
                .build());

        log.debug("Users Loaded: "+userRepository.count());
    }

}