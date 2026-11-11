package com.empresa.ventas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.empresa.ventas.exception.PedidoInvalidoException;
import com.empresa.ventas.model.Producto;
import com.empresa.ventas.repository.ProductoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("VentasService con Mockito")
class VentasServiceMockitoTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VentasService ventasService;

    @Test
    @DisplayName("Producto existente se retorna desde el repositorio")
    void buscarProducto_existente_retornaProducto() {
        // Arrange
        Producto laptop = new Producto("Laptop", 2500.0, 10);
        when(productoRepository.findById(1L)).thenReturn(laptop);

        // Act
        Producto resultado = ventasService.buscarProducto(1L);

        // Assert
        assertEquals("Laptop", resultado.getNombre());
        verify(productoRepository).findById(1L);
    }

    @Test
    @DisplayName("Producto inexistente lanza PedidoInvalidoException")
    void buscarProducto_inexistente_lanzaExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(null);

        PedidoInvalidoException ex = assertThrows(PedidoInvalidoException.class,
                () -> ventasService.buscarProducto(99L));

        assertEquals("Producto no encontrado: 99", ex.getMessage());
    }
}
