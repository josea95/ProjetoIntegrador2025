package com.marmita.entity;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Marmita {
    @Id
    @GeneratedValue
    private Long id;

    private String cliente;
    private Double valor;
    private Double peso;

    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
    }

    public Duration getTempoPreparo() {
        if (dataPedido != null && dataEntrega != null) {
            return Duration.between(dataPedido, dataEntrega);
        }
        return Duration.ZERO;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public LocalDateTime getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDateTime dataEntrega) { this.dataEntrega = dataEntrega; }
}
