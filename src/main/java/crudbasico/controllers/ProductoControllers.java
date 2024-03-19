package crudbasico.controllers;

import crudbasico.models.Producto;
import crudbasico.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("productos")
@CrossOrigin


public class ProductoControllers {

    private final ProductoService productoService;

    public ProductoControllers(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping()
    public ResponseEntity<List<Producto>> mostrarProductos() {
        try {
            List<Producto> productos = productoService.mostrarProductos();
            return !productos.isEmpty() ? ResponseEntity.ok(productos)
                                        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {
            if (producto == null) {
                return ResponseEntity.badRequest().body("Datos de producto incompletos o incorrectos");
            } else {
                productoService.productoSave(producto);
                return ResponseEntity.ok("Producto Guardado Exitosamente!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Interno al guardar el producto");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> productoSaveById(@PathVariable Long id) {
        try {
            Optional<Producto> producto = productoService.productoSaveById(id);
            return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.productoUpdate(id, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok("Producto eliminado correctamente!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto");
        }
    }
}