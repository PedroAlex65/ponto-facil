package com.pontofacil.ponto_facil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pontofacil.ponto_facil.dto.RegistroPontoDto;
import com.pontofacil.ponto_facil.enums.TipoRegistro;
import com.pontofacil.ponto_facil.service.PontoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PontoController.class)
public class PontoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PontoService pontoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 201 ao registrar um ponto válido")
    void deveRetornar201RegistrarPonto() throws Exception {
        //Arrange
        RegistroPontoDto dto = new RegistroPontoDto();
        dto.setFuncionarioId(1L);
        dto.setTipo(TipoRegistro.ENTRADA);
        dto.setDataHora(LocalDateTime.now());

        //Act & Assert
        mockMvc.perform(post("/ponto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()).andDo(print());
    }
}
