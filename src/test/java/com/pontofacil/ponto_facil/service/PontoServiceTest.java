package com.pontofacil.ponto_facil.service;

import com.pontofacil.ponto_facil.dto.RegistroPontoDto;
import com.pontofacil.ponto_facil.enums.TipoRegistro;
import com.pontofacil.ponto_facil.model.RegistroPonto;
import com.pontofacil.ponto_facil.repository.PontoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Faz o Mockito funcionar com JUnit 5
public class PontoServiceTest {

    @Mock
    private PontoRepository pontoRepository; //Cria o dublê do banco

    @InjectMocks
    private PontoService pontoService; //Instancia o service e coloca o dublê lá dentro

    @Test
    @DisplayName("Deve lançar erro quando o último registro no banco é igual ao que o usúario quer marcar.")
    void deveLancarExcecaoQuandoStatusRepetido() {
        //GIVEN
        Long idFuncionario = 1L;

        RegistroPontoDto dto = new RegistroPontoDto();
        dto.setFuncionarioId(idFuncionario);
        dto.setTipo(TipoRegistro.ENTRADA);

        RegistroPonto ultimoPontoFake = new RegistroPonto();
        ultimoPontoFake.setTipo(TipoRegistro.ENTRADA);

        when(pontoRepository.findFirstByFuncionarioIdOrderByDataHoraDesc(idFuncionario)).thenReturn(Optional.of(ultimoPontoFake));

        //WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            pontoService.registrarPonto(dto);
        });
    }


    @Test
    @DisplayName("Deve registrar ponto com sucesso quando o status é diferente do último")
    void deveRegistrarComSucesso() {
        //GIVEN
        Long id = 1L;
        RegistroPontoDto dto = new RegistroPontoDto();
        dto.setFuncionarioId(id);
        dto.setTipo(TipoRegistro.ENTRADA);

        RegistroPonto ultimoPontoFake = new RegistroPonto();
        ultimoPontoFake.setTipo(TipoRegistro.SAIDA);

        when(pontoRepository.findFirstByFuncionarioIdOrderByDataHoraDesc(id)).thenReturn(Optional.of(ultimoPontoFake));
        //Precisamos mockar o save(), se não ele retorna um null
        when(pontoRepository.save(any())).thenReturn(new RegistroPonto());
        //WHEN
        RegistroPontoDto resultado = pontoService.registrarPonto(dto);

        //THEN
        assertNotNull(resultado);
        assertEquals(TipoRegistro.ENTRADA, resultado.getTipo());

        verify(pontoRepository.save(any()));
    }

}
