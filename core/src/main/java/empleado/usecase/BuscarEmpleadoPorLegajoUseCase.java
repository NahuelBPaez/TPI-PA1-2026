package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

public class BuscarEmpleadoPorLegajoUseCase implements BuscarEmpleadoPorLegajoInput {
    private final EmpleadoRepository empleadoRepository;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado buscar(String legajo) {
        return empleadoRepository.buscarPorLegajo(legajo)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(legajo));
    }
}
