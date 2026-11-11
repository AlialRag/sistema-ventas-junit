package com.empresa.ventas.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.empresa.ventas.exception.PagoInsuficienteException;
import com.empresa.ventas.model.Pedido;
import com.empresa.ventas.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Pruebas de VentasService")
class VentasServiceTest {

    private VentasService ventasService;
    private Pedido pedido;
    private Producto laptop;

    @BeforeEach
    void setUp() {
        ventasService = new VentasService();
        pedido = new Pedido();
        laptop = new Producto("Laptop", 2500.00, 10);
    }

    @Nested
    @DisplayName("calcularTotal()")
    class CalcularTotalTest {

        @Test
        @DisplayName("Pedido vacio retorna cero")
        void calcularTotal_pedidoVacio_retornaCero() {
            assertEquals(0.0, ventasService.calcularTotal(pedido), 0.01);
        }

        @Test
        @DisplayName("Un producto retorna precio x cantidad")
        void calcularTotal_unProducto_retornaTotalCorrecto() {
            pedido.agregarItem(laptop, 2);
            assertEquals(5000.0, ventasService.calcularTotal(pedido), 0.01);
        }

        @Test
        @DisplayName("Varios productos suman todos los subtotales")
        void calcularTotal_variosProductos_sumaSubtotales() {
            pedido.agregarItem(laptop, 1);
            pedido.agregarItem(new Producto("Mouse", 50.0, 100), 3);
            assertEquals(2650.0, ventasService.calcularTotal(pedido), 0.01);
        }

        @Test
        @DisplayName("Pedido null lanza NullPointerException")
        void calcularTotal_pedidoNull_lanzaNPE() {
            assertThrows(NullPointerException.class,
                    () -> ventasService.calcularTotal(null));
        }
    }

    @Nested
    @DisplayName("aplicarDescuento()")
    class AplicarDescuentoTest {

        @BeforeEach
        void agregarItem() {
            pedido.agregarItem(new Producto("Articulo", 1000.0, 10), 1);
        }

        @ParameterizedTest(name = "{0}% de descuento sobre 1000 = {1}")
        @CsvSource({
                "0.0,   1000.0",
                "10.0,   900.0",
                "50.0,   500.0",
                "100.0,    0.0"
        })
        @DisplayName("Diferentes porcentajes se calculan correctamente")
        void aplicarDescuento_porcentajeValido_retornaTotalConDescuento(
                double porcentaje, double esperado) {
            double resultado = ventasService.aplicarDescuento(pedido, porcentaje);
            assertEquals(esperado, resultado, 0.01);
        }

        @ParameterizedTest(name = "Porcentaje invalido: {0}")
        @ValueSource(doubles = {-0.01, -1.0, 100.01, 150.0})
        @DisplayName("Porcentajes fuera de [0,100] lanzan IllegalArgumentException")
        void aplicarDescuento_porcentajeInvalido_lanzaExcepcion(double invalido) {
            assertThrows(IllegalArgumentException.class,
                    () -> ventasService.aplicarDescuento(pedido, invalido));
        }
    }

    @Nested
    @DisplayName("procesarPago()")
    class ProcesarPagoTest {

        @BeforeEach
        void agregarItem() {
            pedido.agregarItem(laptop, 1); // total = 2500
        }

        @Test
        @DisplayName("Pago exacto retorna vuelto cero")
        void procesarPago_montoExacto_retornaCero() throws PagoInsuficienteException {
            assertEquals(0.0, ventasService.procesarPago(pedido, 2500.0), 0.01);
        }

        @Test
        @DisplayName("Pago mayor retorna el vuelto")
        void procesarPago_montoMayor_retornaVuelto() throws PagoInsuficienteException {
            assertEquals(500.0, ventasService.procesarPago(pedido, 3000.0), 0.01);
        }

        @Test
        @DisplayName("Pago menor lanza PagoInsuficienteException")
        void procesarPago_montoMenor_lanzaExcepcion() {
            PagoInsuficienteException ex = assertThrows(PagoInsuficienteException.class,
                    () -> ventasService.procesarPago(pedido, 2000.0));
            assertEquals("Monto insuficiente. Total a pagar: S/ 2500.0",
                    ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Modelo: Producto y Pedido")
    class ModeloTest {

        @Test
        @DisplayName("Producto con precio cero es invalido")
        void producto_precioCero_lanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Producto("Gratis", 0.0, 1));
        }

        @Test
        @DisplayName("Producto con datos invalidos lanza IllegalArgumentException")
        void producto_datosInvalidos_lanzaExcepcion() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Producto(" ", 10.0, 1)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Producto("X", -5.0, 1)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Producto("X", 10.0, -1)));
        }

        @Test
        @DisplayName("reducirStock descuenta unidades")
        void reducirStock_cantidadValida_descuentaStock() {
            laptop.reducirStock(4);
            assertEquals(6, laptop.getStock());
        }

        @Test
        @DisplayName("reducirStock mayor al stock lanza IllegalStateException")
        void reducirStock_mayorAlStock_lanzaExcepcion() {
            assertThrows(IllegalStateException.class, () -> laptop.reducirStock(11));
        }

        @Test
        @DisplayName("reducirStock con cantidad no positiva lanza excepcion")
        void reducirStock_cantidadCero_lanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> laptop.reducirStock(0));
        }

        @Test
        @DisplayName("agregarItem con datos invalidos lanza excepcion")
        void agregarItem_datosInvalidos_lanzaExcepcion() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> pedido.agregarItem(null, 1)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> pedido.agregarItem(laptop, 0)));
        }

        @Test
        @DisplayName("getItems devuelve una vista de solo lectura")
        void getItems_listaNoModificable() {
            pedido.agregarItem(laptop, 1);
            assertEquals(1, pedido.getItems().size());
            assertThrows(UnsupportedOperationException.class,
                    () -> pedido.getItems().clear());
        }
    }
}
