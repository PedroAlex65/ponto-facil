package com.pontofacil.ponto_facil.dto;

import com.pontofacil.ponto_facil.enums.TipoRegistro;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistroPontoDto {

    @NotNull(message = "O ID do funcionario é obrigatório")
    private Long funcionarioId;
    @NotNull(message = "O data e hora é obrigatório")
    private LocalDateTime dataHora;
    @NotNull(message = "O tipo (ENTRADA/SAIDA) é obrigatório")
    private TipoRegistro tipo;
}
