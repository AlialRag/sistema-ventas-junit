package com.empresa.ventas.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private final List<ItemPedido> items = new ArrayList<>();

    public void agregarItem(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser null");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        items.add(new ItemPedido(producto, cantidad));
    }

    public List<ItemPedido> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double calcularTotal() {
        return items.stream().mapToDouble(ItemPedido::subtotal).sum();
    }
}
