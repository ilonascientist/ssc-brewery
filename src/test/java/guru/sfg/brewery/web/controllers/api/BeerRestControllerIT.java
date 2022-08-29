package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests{

        public Beer beerToDelete(){
            Random random = new Random();
            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("DeleteMe Beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(random.nextInt(99999999)))
                    .build());
        }

        @Test
        void deleteBeerHttpBasicAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/"+beerToDelete().getId())
                            .with(httpBasic("spring", "test")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/"+beerToDelete().getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/"+beerToDelete().getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/"+beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }

    }

    @Test
    void findBeersTest() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByIdTest() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beer/"+beer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void findByUpcTest() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beerUpc/"+beer.getUpc()))
                .andExpect(status().isOk());
    }

    @Test
    void  findBeerFormADMIN() throws Exception {
        mockMvc.perform(get("/beers").param("beerName", "")
                .with(httpBasic("spring", "test"))).andExpect(status().isOk());

    }
}