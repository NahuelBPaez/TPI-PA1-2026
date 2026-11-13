package ar.edu.undec.adapter.data.empleado.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EmpleadoDataMapperTest {

    private final EmpleadoDataMapper mapper = new EmpleadoDataMapper();

    @Test
    void aEntidad_conEmpleadoValido_copiaTodosLosCampos() {
        Empleado empleado = empleadoEjemplo();

        EmpleadoEntity entidad = mapper.aEntidad(empleado);

        assertEquals(empleado.getId(), entidad.getId());
        assertEquals(empleado.getNombreCompleto(), entidad.getNombreCompleto());
        assertEquals(empleado.getLegajo(), entidad.getLegajo());
        assertEquals(empleado.getFechaIngreso(), entidad.getFechaIngreso());
        assertEquals(empleado.getPuesto(), entidad.getPuesto());
        assertEquals(empleado.getSalario(), entidad.getSalario());
    }

    @Test
    void aDominio_conEntidadValida_reconstruyeElEmpleado() {
        Empleado empleado = empleadoEjemplo();
        EmpleadoEntity entidad = new EmpleadoEntity(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getLegajo(),
                empleado.getFechaIngreso(),
                empleado.getPuesto(),
                empleado.getSalario()
        );

        Empleado resultado = mapper.aDominio(entidad);

        assertEquals(empleado.getId(), resultado.getId());
        assertEquals(empleado.getNombreCompleto(), resultado.getNombreCompleto());
        assertEquals(empleado.getLegajo(), resultado.getLegajo());
        assertEquals(empleado.getFechaIngreso(), resultado.getFechaIngreso());
        assertEquals(empleado.getPuesto(), resultado.getPuesto());
        assertEquals(empleado.getSalario(), resultado.getSalario());
    }

    private Empleado empleadoEjemplo() {
        return Empleado.instance(
                UUID.randomUUID(),
                "Ana Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );
    }
}
