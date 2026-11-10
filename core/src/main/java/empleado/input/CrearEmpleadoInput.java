package empleado.input;

import empleado.modelo.Empleado;

public interface CrearEmpleadoInput {
    Empleado crear(CrearEmpleadoComando comando);
}
