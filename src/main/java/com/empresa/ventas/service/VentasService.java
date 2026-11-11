package com.empresa.ventas.service;

import com.empresa.ventas.exception.PagoInsuficienteException;
import com.empresa.ventas.exception.PedidoInvalidoException;
import com.empresa.ventas.model.Pedido;
import com.empresa.ventas.model.Producto;
import com.empresa.ventas.repository.ProductoRepository;

public class VentasService {

    private final ProductoRepository productoRepository;

    public VentasService() {
        this(null);
    }

    public VentasService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public double calcularTotal(Pedido pedido) {
        if (pedido == null) {
            throw new NullPointerException("El pedido no puede ser null");
        }
        return pedido.calcularTotal();
    }

    public double aplicarDescuento(Pedido pedido, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 0 y 100");
        }
        double total = calcularTotal(pedido);
        return total - (total * porcentaje / 100);
    }

    /** Devuelve el vuelto. Lanza PagoInsuficienteException si el monto no alcanza. */
    public double procesarPago(Pedido pedido, double monto)
            throws PagoInsuficienteException {
        double total = calcularTotal(pedido);
        if (monto < total) {
            throw new PagoInsuficienteException(
                    "Monto insuficiente. Total a pagar: S/ " + total);
        }
        return monto - total;
    }

    public Producto buscarProducto(long id) {
        Producto producto = productoRepository.findById(id);
        if (producto == null) {
            throw new PedidoInvalidoException("Producto no encontrado: " + id);
        }
        return producto;
    }
}
