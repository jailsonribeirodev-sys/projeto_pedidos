package com.pedidos.projetoAPI.entities.enums;

/**
 * Enum que representa os possíveis estados de um Pedido no sistema.
 * Armazena um código numérico para facilitar o persistência no banco de dados.
 */
public enum StatusPedido {
    AGUARDANDO_PAGAMENTO(1),
    PAGO(2),
    ENVIADO(3),
    ENTREGUE(4),
    CANCELADO(5);

    private final int code;

    StatusPedido(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Converte um código numérico vindo do banco de dados para o respectivo Enum StatusPedido.
     * @param code Código numérico (ex: 1, 2)
     * @return StatusPedido correspondente
     * @throws IllegalArgumentException caso o código informado seja inválido
     */
    public static StatusPedido valueOf(int code) {
        for (StatusPedido value : StatusPedido.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código de StatusPedido inválido: " + code);
    }
}
