package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class EmpleadoRepoImplementacion implements EmpleadoRepository {
    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoDataMapper empleadoDataMapper;

    public EmpleadoRepoImplementacion(
            EmpleadoCrudRepository empleadoCrudRepository,
            EmpleadoDataMapper empleadoDataMapper
    ) {
        this.empleadoCrudRepository = empleadoCrudRepository;
        this.empleadoDataMapper = empleadoDataMapper;
    }

    @Override
    public boolean existePorLegajo(String legajo) {
        return empleadoCrudRepository.existsByLegajo(legajo);
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        return empleadoCrudRepository.findByLegajo(legajo).map(empleadoDataMapper::aDominio);
    }

    @Override
    public boolean guardar(Empleado empleado) {
        try {
            empleadoCrudRepository.save(empleadoDataMapper.aEntidad(empleado));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<Empleado> listar() {
        return empleadoCrudRepository.findAll().stream()
                .map(empleadoDataMapper::aDominio)
                .toList();
    }
}
