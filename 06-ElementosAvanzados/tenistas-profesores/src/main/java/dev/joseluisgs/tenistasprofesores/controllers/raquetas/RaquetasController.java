package dev.joseluisgs.tenistasprofesores.controllers.raquetas;

import dev.joseluisgs.tenistasprofesores.dto.Raquetas.RaquetaRequestDto;
import dev.joseluisgs.tenistasprofesores.dto.Raquetas.RaquetaResponseDto;
import dev.joseluisgs.tenistasprofesores.mapper.RaquetaMapper;
import dev.joseluisgs.tenistasprofesores.services.Raquetas.RaquetasService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador de Raquetas
 * Aquí se implementan los métodos de la API REST
 * Es un controlador REST, por lo que se le indica con la anotación @RestController
 * El path base de la API REST es /api/raquetas y se le indica con la anotación @RequestMapping
 */
@RestController
@RequestMapping("/api/raquetas")
@Slf4j // Para el log
public class RaquetasController {
    // Mis dependecias
    private final RaquetasService raquetasService;
    private final RaquetaMapper raquetaMapper;

    // Inyectamos el repositorio de raquetas con la anotación @Autowired
    @Autowired
    public RaquetasController(RaquetasService raquetasService, RaquetaMapper raquetaMapper) {
        this.raquetasService = raquetasService;
        this.raquetaMapper = raquetaMapper;
    }

    // Aquí se implementan los métodos de la API REST

    // GET: /api/raquetas
    @GetMapping("")
    public ResponseEntity<List<RaquetaResponseDto>> getAllRaquetas(
            @RequestParam @Nullable String marca
    ) {
        log.info("getAllRaquetas");

        // Jugamos con query params / api/raquetas?marca=Wilson
        if (marca != null && !marca.isEmpty()) {
            return ResponseEntity.ok(
                    raquetaMapper.toResponse(raquetasService.findAllByMarca(marca))
            );
        }
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findAll())
        );
    }

    // GET: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @GetMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> getRaquetaById(
            @PathVariable Long id
    ) {
        log.info("getRaquetaById");
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findById(id))
        );
    }

    @GetMapping("/find/{uuid}")
    public ResponseEntity<RaquetaResponseDto> getRaquetaByUuid(
            @PathVariable UUID uuid
    ) {
        log.info("getRaquetaByUuid");
        // Existe la raqueta?
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.findByUuid(uuid))
        );
    }

    // POST: /api/raquetas
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PostMapping
    public ResponseEntity<RaquetaResponseDto> postRaqueta(
            @RequestBody RaquetaRequestDto raqueta
    ) {
        log.info("addRaqueta");
        // Devolvemos created
        return ResponseEntity.created(null).body(
                raquetaMapper.toResponse(
                        raquetasService.save(raquetaMapper.toModel(raqueta)))
        );
    }

    // PUT: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    // @RequestBody: Indica que el parámetro de la función es un parámetro del cuerpo de la petición HTTP
    @PutMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> putRaqueta(
            @PathVariable Long id,
            @RequestBody RaquetaRequestDto raqueta
    ) {
        log.info("putRaqueta");
        // Devolvemos el OK
        return ResponseEntity.ok(
                raquetaMapper.toResponse(raquetasService.update(id, raquetaMapper.toModel(raqueta)))
        );
    }

    // DELETE: /api/raquetas/{id}
    // @PathVariable: Indica que el parámetro de la función es un parámetro de la URL en este caso {id}
    @DeleteMapping("/{id}")
    public ResponseEntity<RaquetaResponseDto> deleteRaqueta(
            @PathVariable Long id
    ) {
        log.info("deleteRaqueta");
        raquetasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
