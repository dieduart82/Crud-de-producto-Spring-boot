package crudbasico.service;


import crudbasico.models.Producto;
import crudbasico.repository.ProductoRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> mostrarProductos() {
        return (List<Producto>) productoRepository.findAll();
    }

    public Optional<Producto> productoSaveById(Long id) {
        return productoRepository.findById(id);
    }

    public void productoSave(Producto producto) {
        productoRepository.save(producto);
    }

    public Producto productoUpdate(Long id, Producto producto) {
        Optional<Producto> existeProducto = productoRepository.findById(id);
        if (existeProducto.isPresent()) {
            Producto productoExistente = existeProducto.get();

            productoExistente.setNombre(productoExistente.getNombre());
            productoExistente.setApellido(productoExistente.getApellido());
            productoExistente.setEdad(productoExistente.getEdad());
            productoExistente.setEmail(productoExistente.getEmail());
            return productoRepository.save(productoExistente);
        } else {
            throw new NoSuchElementException("Producto no encontrado");
        }
    }

    public void deleteProducto(Long id) {
        productoRepository.findById(id)
                .ifPresentOrElse(
                        producto -> productoRepository.deleteById(id),
                        () -> {
                            throw new NoSuchElementException("Producto no encontrado");
                        }
                );
    }
}