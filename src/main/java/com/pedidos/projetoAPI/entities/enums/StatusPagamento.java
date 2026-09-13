package com.pedidos.projetoAPI.entities.enums;

/**
 * Enum que representa a situação do pagamento de um pedido.
 */
public enum StatusPagamento {
    PENDENTE(1),
    QUITADO(2),
    CANCELADO(3);

    private final int code;

    StatusPagamento(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Converte um código numérico para o Enum StatusPagamento correspondente.
     */
    public static StatusPagamento valueOf(int code) {
        for (StatusPagamento value : StatusPagamento.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código de StatusPagamento inválido: " + code);
    }
}
