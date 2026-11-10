package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.exception.PersistenciaEmpleadoException;
import empleado.input.CrearEmpleadoComando;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;
import java.util.UUID;

public class CrearEmpleadoUseCase implements CrearEmpleadoInput {
    private final EmpleadoRepository empleadoRepository;

    public CrearEmpleadoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado crear(CrearEmpleadoComando comando) {
        Empleado empleado = Empleado.instance(
                UUID.randomUUID(),
                comando.nombreCompleto(),
                comando.legajo(),
                comando.fechaIngreso(),
                comando.puesto(),
                comando.salario()
        );
        if (empleadoRepository.existePorLegajo(comando.legajo())) {
            throw new LegajoDuplicadoException(comando.legajo());
        }
        if (!empleadoRepository.guardar(empleado)) {
            throw new PersistenciaEmpleadoException(
                    "No se pudo guardar el empleado con legajo '" + comando.legajo() + "'"
            );
        }
        return empleado;
    }
}
