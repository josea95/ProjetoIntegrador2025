package org.example.enums;

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

    // Se o pedido estiver na fila, ele passa para preparando.
    // Se estiver preparando, permanece até que mude para finalizado.
    public StatusPedido proximo() {
        switch (this) {
            case FILA:
                return PREPARANDO;
            case PREPARANDO:
                return PREPARANDO;
            default:
                return this;
        }
    }

}