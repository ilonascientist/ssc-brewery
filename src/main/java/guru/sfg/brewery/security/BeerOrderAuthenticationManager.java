package guru.sfg.brewery.security;


import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManager {

    //verifies that customer acts only on their orders

    public boolean customerIdMatchers(Authentication authentication, UUID customerId){
        User authUser = (User) authentication.getPrincipal();

        log.debug("Auth User Customer Id: "+authUser.getCustomer().getId()+" Customer Id: "+ customerId);

        return authUser.getCustomer().getId().equals(customerId);
    }
}
