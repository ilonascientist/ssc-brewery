package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerUrl() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000")
                        .param("apiKey", "spring")
                        .param("apiSecret", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerUrlBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000")
                        .param("apiKey", "spring")
                        .param("apiSecret", "testXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerTest() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "test"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "testXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeersTest() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isOk());
    }

    @Test
    void findByUpcTest() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/063123420036"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(status().isUnauthorized());
    }

}