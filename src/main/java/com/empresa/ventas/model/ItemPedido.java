package com.empresa.ventas.model;

public class ItemPedido {

    private final Producto producto;
    private final int cantidad;

    public ItemPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad()      { return cantidad; }

    public double subtotal() {
        return producto.getPrecio() * cantidad;
    }
}
