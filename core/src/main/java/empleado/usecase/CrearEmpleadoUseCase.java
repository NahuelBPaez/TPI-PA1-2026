package empleado.usecase;

import empleado.input.CrearEmpleadoComando;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

public class CrearEmpleadoUseCase implements CrearEmpleadoInput {
    private final EmpleadoRepository empleadoRepository;

    public CrearEmpleadoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado crear(CrearEmpleadoComando comando) {
        throw new UnsupportedOperationException();
    }
}
