package dev.joseluisgs.tenistasprofesores.models.raquetas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase Raqueta POJO
 */

// Data: Genera los getters y setters, toString, equals, hashCode y el constructor con todos los parámetros necesarios (finals)
@Data
@AllArgsConstructor
public class Raqueta {
    private final Long id;
    private UUID uuid;
    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;
    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;
    private String imagen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;

    public Raqueta(long id, UUID uuid, String testMarca, String testModelo, double precio, String testImagen) {
        this.id = id;
        this.uuid = uuid;
        this.marca = testMarca;
        this.modelo = testModelo;
        this.precio = precio;
        this.imagen = testImagen;
    }
}
