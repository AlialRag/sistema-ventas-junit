package com.empresa.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    @DisplayName("Sumar dos positivos retorna su suma")
    void sumar_dosPositivos_retornaSuma() {
        // Arrange (calculadora creada en setUp)
        // Act
        int resultado = calculadora.sumar(3, 5);
        // Assert
        assertEquals(8, resultado);
    }

    @Test
    @DisplayName("Restar retorna la diferencia")
    void restar_dosEnteros_retornaDiferencia() {
        assertEquals(2, calculadora.restar(5, 3));
    }

    @Test
    @DisplayName("Dividir entre un numero distinto de cero retorna el cociente")
    void dividir_divisorValido_retornaCociente() {
        assertEquals(2.5, calculadora.dividir(5, 2), 0.0001);
    }

    @Test
    @DisplayName("Dividir por cero lanza ArithmeticException")
    void dividir_divisorCero_lanzaArithmeticException() {
        ArithmeticException ex = assertThrows(ArithmeticException.class,
                () -> calculadora.dividir(10, 0));
        assertEquals("No se puede dividir por cero", ex.getMessage());
    }
}
