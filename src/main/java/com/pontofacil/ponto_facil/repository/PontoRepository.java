package com.pontofacil.ponto_facil.repository;

import com.pontofacil.ponto_facil.model.RegistroPonto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PontoRepository extends JpaRepository<RegistroPonto, Long> {
    Optional<RegistroPonto> findFirstByFuncionarioIdOrderByDataHoraDesc(Long funcionarioId);
}
