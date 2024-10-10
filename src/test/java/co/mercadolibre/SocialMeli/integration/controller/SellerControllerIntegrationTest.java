package co.mercadolibre.SocialMeli.integration.controller;

import co.mercadolibre.SocialMeli.dto.response.CountFollowersDTO;
import co.mercadolibre.SocialMeli.dto.response.SellerFollowersDTO;
import co.mercadolibre.SocialMeli.util.IntegrationData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("T0003 - Lista seguidores - Camino Bueno")
    public void listFollowersGoodTest() throws Exception {

        int userId = 1;

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/list", userId))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        SellerFollowersDTO followersResponse = objectMapper.readValue(response.getResponse().getContentAsString(), SellerFollowersDTO.class);
        Assertions.assertEquals(IntegrationData.getListFollowersDTO(), followersResponse);

    }

    @Test
    @DisplayName("T0003 - Lista seguidores - Camino malo, vendedor no encontrado")
    public void listFollowersNotFoundSellerTest() throws Exception {

        int userId = 10;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/list", userId))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("El usuario con el id %d no se ha encontrado".formatted(userId)));

    }

    @Test
    @DisplayName("T0003 - Lista seguidores - Camino malo, no es vendedor")
    public void listFollowersNotASellerTest() throws Exception {

        int userId = 2;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/list", userId))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("El usuario con el id %d no es un vendedor".formatted(userId)));

    }

    @Nested
    class CountFollowersT0007 {
        @DisplayName("T-0007: Contar seguidores")
        @Test
        void countFollowersOkTest() throws Exception{

            int userId = 1;

            MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", userId))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andReturn();

            CountFollowersDTO countSellerResponse = objectMapper.readValue(response.getResponse().getContentAsString(), CountFollowersDTO.class);
            Assertions.assertEquals(IntegrationData.getCountSellerDTO(), countSellerResponse);
        }
        @DisplayName("T-0007: No se encuentra el usuario")
        @Test
        void countFollowersNotUserFoundTest() throws Exception{
            int userId = 10;

            mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", userId))
                    .andDo(print()).andExpect(status().isNotFound())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                            .value("El usuario con el id %d no se ha encontrado".formatted(userId)));
        }
        @DisplayName("T-0007: El usuario no es vendedor")
        @Test
        void countFollowersNotSellerTest() throws Exception {
            int userId = 2;

            mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", userId))
                    .andDo(print()).andExpect(status().isNotFound())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                            .value("El usuario con el id %d no es un vendedor".formatted(userId)));
        }
    }

}
