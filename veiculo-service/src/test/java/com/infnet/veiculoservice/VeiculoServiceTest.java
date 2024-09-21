package com.infnet.veiculoservice;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VeiculoServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Deve inserir um veículo no banco (POST /api/veiculos)")
    public void testaInsert() throws Exception {
        String veiculoJson = """
            {
                "modelo": "Z3",
                "marca": "BMW",
                "ano": 2002
            }
        """;

        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.modelo").value("Z3"))
                .andExpect(jsonPath("$.marca").value("BMW"))
                .andExpect(jsonPath("$.ano").value(2002));
    }

    @Test
    @DisplayName("Deve retornar todos os veículos (GET /api/veiculos)")
    public void testaGetAll() throws Exception {
        mockMvc.perform(get("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Deve buscar um veículo pelo ID (GET /api/veiculos/{id})")
    public void testaGetById() throws Exception {
        String veiculoJson = """
            {
                "modelo": "Golf",
                "marca": "Volkswagen",
                "ano": 2010
            }
        """;

        MvcResult result = mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long id = JsonPath.parse(responseBody).read("$.id", Long.class);

        mockMvc.perform(get("/api/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.modelo").value("Golf"))
                .andExpect(jsonPath("$.marca").value("Volkswagen"))
                .andExpect(jsonPath("$.ano").value(2010));
    }

    @Test
    @DisplayName("Deve atualizar um veículo existente (PUT /api/veiculos/{id})")
    public void testaUpdate() throws Exception {
        String veiculoJson = """
            {
                "modelo": "Corolla",
                "marca": "Toyota",
                "ano": 2015
            }
        """;

        MvcResult result = mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long id = JsonPath.parse(responseBody).read("$.id", Long.class);

        String veiculoAtualizadoJson = """
            {
                "modelo": "Civic",
                "marca": "Honda",
                "ano": 2018
            }
        """;

        mockMvc.perform(put("/api/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.marca").value("Honda"))
                .andExpect(jsonPath("$.ano").value(2018));
    }

    @Test
    @DisplayName("Deve remover um veículo do banco de dados (DELETE /api/veiculos/{id})")
    public void testaDelete() throws Exception {
        String veiculoJson = """
            {
                "modelo": "Polo",
                "marca": "Volkswagen",
                "ano": 2019
            }
        """;

        MvcResult result = mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long id = JsonPath.parse(responseBody).read("$.id", Long.class);

        mockMvc.perform(delete("/api/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/veiculos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

