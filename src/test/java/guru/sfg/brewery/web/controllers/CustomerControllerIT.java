package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerIT extends BaseIT{

    @ParameterizedTest(name = "[{index}] with [{arguments}]")
    @MethodSource("getStreamAdminCustomer")
    void testListCustomersAuth(String username, String password) throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic(username, password)))
                .andExpect(status().isOk());
    }

    @Test
    void testListCustomersNoAuth() throws Exception {
        mockMvc.perform(get("/customers")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testListCustomersNoLogin() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }
}
