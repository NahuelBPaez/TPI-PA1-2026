package ar.edu.undec.adapter.data.empleado.crud;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoCrudRepository extends JpaRepository<EmpleadoEntity, UUID> {
    boolean existsByLegajo(String legajo);

    Optional<EmpleadoEntity> findByLegajo(String legajo);
}
