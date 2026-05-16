package com.pontofacil.ponto_facil.service;

import com.pontofacil.ponto_facil.dto.RegistroPontoDto;
import com.pontofacil.ponto_facil.enums.TipoRegistro;
import com.pontofacil.ponto_facil.model.RegistroPonto;
import com.pontofacil.ponto_facil.repository.PontoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PontoService {

    @Autowired
    private PontoRepository pontoRepository;

    public RegistroPontoDto registrarPonto(RegistroPontoDto registroPontoDto) {
        Optional<RegistroPonto> ultimoRegistro = pontoRepository.findFirstByFuncionarioIdOrderByDataHoraDesc(registroPontoDto.getFuncionarioId());

        if (ultimoRegistro.isPresent()) {
            TipoRegistro tipoNoBanco = ultimoRegistro.get().getTipo();

            if (registroPontoDto.getTipo() == tipoNoBanco) {
                throw new RuntimeException("O colaborador já está no status: " + tipoNoBanco);
            }
        }
        if (ultimoRegistro.isEmpty() && registroPontoDto.getTipo() == TipoRegistro.SAIDA) {
            throw new RuntimeException("Primeiro registro deve ser obrigatoriamente uma ENTRADA");
        }
        RegistroPonto novoPonto = new RegistroPonto();
        novoPonto.setFuncionarioId(registroPontoDto.getFuncionarioId());
        novoPonto.setDataHora(registroPontoDto.getDataHora());
        novoPonto.setTipo(registroPontoDto.getTipo());

        //Salvar
        RegistroPonto salvo = pontoRepository.save(novoPonto);

        //Retornar o DTO
        return registroPontoDto;
    }
}
