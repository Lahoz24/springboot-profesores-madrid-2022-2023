package dev.joseluisgs.tenistasprofesores.repositories.raquetas;

import dev.joseluisgs.tenistasprofesores.data.raquetas.RaquetasFactory;
import dev.joseluisgs.tenistasprofesores.models.Raqueta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de la interfaz RaquetasRepository para la gestión de datos
 */

// Repository: Indica que es un repositorio de datos para Spring
@Repository
// Slf4j: Nos permite usar el Logger
@Slf4j
public class RaquetasRepositoryImpl implements RaquetasRepository {

    // Creamos un mapa de raquetas
    private final Map<Long, Raqueta> raquetas = RaquetasFactory.getRaquetasDemoData();

    /**
     * Devolvemos todas las raquetas
     *
     * @return Iterable<Raqueta> con todas las raquetas
     */
    @Override
    public Iterable<Raqueta> findAll() {
        log.info("findAll");
        // Devolvemos el mapa de raquetas
        return raquetas.values();
    }

    /**
     * Devolvemos una raqueta por su ID
     *
     * @param id ID de la raqueta
     * @return Optional<Raqueta> con la raqueta
     */
    @Override
    public Optional<Raqueta> findById(Long id) {
        log.info("findById");
        // Devolvemos la raqueta por su ID
        return Optional.ofNullable(raquetas.get(id));
    }

    /**
     * Comprobamos si existe una raqueta por su ID
     *
     * @param id ID de la raqueta
     * @return Boolean con el resultado
     */
    @Override
    public Boolean existsById(Long id) {
        log.info("existsById");
        // Comprobamos si existe la raqueta por su ID
        return raquetas.containsKey(id);
    }

    /**
     * Guardamos/Actualizamos una raqueta
     *
     * @param raqueta Raqueta a guardar
     * @return Raqueta guardada
     */
    @Override
    public Raqueta save(Raqueta raqueta) {
        log.info("save");
        // Guardamos la raqueta en el mapa, si no existe la crea
        // Existe?
        if (raquetas.containsKey(raqueta.getId())) {
            // Si existe la actualizamos
            return update(raqueta);

        } else {
            // Si no existe la creamos
            return create(raqueta);
        }
    }

    /**
     * Creamos una raqueta
     *
     * @param raqueta Raqueta a crear
     * @return Raqueta creada
     */
    private Raqueta create(Raqueta raqueta) {
        log.info("create");
        // Lo primero es obtener el último ID
        long lastId = raquetas.keySet().stream().max(Long::compareTo).orElse(0L);
        // Aumentamos el ID en 1
        lastId++;
        LocalDateTime now = LocalDateTime.now();
        // Asignamos el nuevo ID a la raqueta, el UUID y actualizamos el createdAt y el update
        var newRaqueta = new Raqueta(
                lastId,
                UUID.randomUUID(),
                raqueta.getMarca(),
                raqueta.getModelo(),
                raqueta.getPrecio(),
                raqueta.getImagen(),
                now,
                now,
                false
        );
        // Guardamos la raqueta en el mapa
        raquetas.put(lastId, newRaqueta);
        // Devolvemos la raqueta
        return newRaqueta;
    }

    /**
     * Actualizamos una raqueta
     *
     * @param raqueta Raqueta a actualizar
     * @return Raqueta actualizada
     */
    private Raqueta update(Raqueta raqueta) {
        log.info("update");
        var now = LocalDateTime.now();
        // Obtenemos la raqueta por su ID
        var raquetaToUpdate = raquetas.get(raqueta.getId());
        // Actualizamos los datos que pueden ser modificados
        raquetaToUpdate.setModelo(raqueta.getModelo());
        raquetaToUpdate.setPrecio(raqueta.getPrecio());
        raquetaToUpdate.setImagen(raqueta.getImagen());
        raquetaToUpdate.setUpdatedAt(now);
        // Guardamos la raqueta en el mapa
        raquetas.put(raqueta.getId(), raquetaToUpdate);
        // Devolvemos la raqueta
        return raquetaToUpdate;
    }

    /**
     * Borramos una raqueta por su ID
     *
     * @param id ID de la raqueta
     */
    @Override
    public void deleteById(Long id) {
        log.info("deleteById");
        // Borramos la raqueta por su ID
        raquetas.remove(id);
    }

    /**
     * Borramos una raqueta
     *
     * @param raqueta Raqueta a borrar
     */
    @Override
    public void delete(Raqueta raqueta) {
        log.info("delete");
        // Borramos la raqueta por su ID
        raquetas.remove(raqueta.getId());
    }

    /**
     * Devolvemos el número de raquetas
     *
     * @return long con el número de raquetas
     */
    @Override
    public long count() {
        log.info("count");
        // Devolvemos el número de raquetas
        return raquetas.size();
    }

    /**
     * Borramos todas las raquetas
     */
    @Override
    public void deleteAll() {
        log.info("deleteAll");
        // Borramos todas las raquetas
        raquetas.clear();
    }

    /**
     * Devolvemos todas las raquetas de una marca
     *
     * @param marca Marca de las raquetas
     * @return Iterable<Raqueta> con las raquetas de una marca
     */
    @Override
    public Iterable<Raqueta> findAllByMarca(String marca) {
        log.info("findAllByMarca");
        // Devolvemos todas las raquetas de una marca
        return raquetas.values().stream()
                .filter(raqueta -> raqueta.getMarca().toLowerCase().contains(marca.toLowerCase()))
                .toList();
    }

    @Override
    public Optional<Raqueta> findByUuid(UUID uuid) {
        return raquetas.values().stream()
                .filter(raqueta -> raqueta.getUuid().equals(uuid))
                .findFirst();
    }
}
