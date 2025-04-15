package com.marmita.repository;

import com.marmita.entity.Marmita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MarmitaRepository extends JpaRepository<Marmita, Long> {

    @Query("SELECT m FROM Marmita m WHERE m.dataPedido BETWEEN :inicio AND :fim")
    List<Marmita> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
