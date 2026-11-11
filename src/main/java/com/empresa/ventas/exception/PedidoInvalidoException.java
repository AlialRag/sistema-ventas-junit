package com.empresa.ventas.exception;

/** Excepción no verificada: error de uso o datos inconsistentes. */
public class PedidoInvalidoException extends RuntimeException {

    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
