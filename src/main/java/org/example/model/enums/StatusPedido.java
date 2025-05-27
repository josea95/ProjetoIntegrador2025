package org.example.model.enums;

public enum StatusPedido {
    FILA( "Fila" ),
    PREPARANDO( "Preparando" ),
    FINALIZADO( "Finalizado" ),
    CANCELADO( "Cancelado" );

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}