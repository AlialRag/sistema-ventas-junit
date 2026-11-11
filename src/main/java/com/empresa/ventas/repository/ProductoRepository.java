package com.empresa.ventas.repository;

import com.empresa.ventas.model.Producto;

/**
 * Dependencia externa (por ejemplo, una base de datos).
 * En las pruebas unitarias se reemplaza por un mock de Mockito.
 */
public interface ProductoRepository {

    /** Devuelve el producto o null si no existe. */
    Producto findById(long id);
}
