package ar.edu.undec.adapter.service.empleado.mapper;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.CrearEmpleadoComando;
import empleado.modelo.Empleado;

public class EmpleadoWebMapper {
    public CrearEmpleadoComando aComando(EmpleadoRequest request) {
        return new CrearEmpleadoComando(
                request.nombreCompleto(),
                request.legajo(),
                request.fechaIngreso(),
                request.puesto(),
                request.salario()
        );
    }

    public EmpleadoResponse aRespuesta(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getLegajo(),
                empleado.getFechaIngreso(),
                empleado.getPuesto(),
                empleado.getSalario()
        );
    }
}
