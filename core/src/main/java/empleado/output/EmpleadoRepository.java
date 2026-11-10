package empleado.output;

import empleado.modelo.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository {
    boolean existePorLegajo(String legajo);

    Optional<Empleado> buscarPorLegajo(String legajo);

    boolean guardar(Empleado empleado);

    List<Empleado> listar();
}
