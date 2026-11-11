package com.empresa.ventas.exception;

/** Excepción verificada: el cliente debe manejarla explícitamente. */
public class PagoInsuficienteException extends Exception {

    public PagoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
