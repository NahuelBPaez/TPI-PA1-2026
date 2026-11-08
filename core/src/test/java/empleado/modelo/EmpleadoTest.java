package empleado.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class EmpleadoTest {

    @Test
    void seCreaUnEmpleadoConDatosValidosYConservaTodosSusValores() {
        UUID id = UUID.randomUUID();
        String nombreCompleto = "Ana Pérez";
        String legajo = "ABC1234";
        LocalDate fechaIngreso = LocalDate.now().minusDays(1);
        Puesto puesto = Puesto.ANALISTA;
        BigDecimal salario = new BigDecimal("500000");

        Empleado empleado = Empleado.instance(
                id,
                nombreCompleto,
                legajo,
                fechaIngreso,
                puesto,
                salario
        );

        assertEquals(id, empleado.getId());
        assertEquals(nombreCompleto, empleado.getNombreCompleto());
        assertEquals(legajo, empleado.getLegajo());
        assertEquals(fechaIngreso, empleado.getFechaIngreso());
        assertEquals(puesto, empleado.getPuesto());
        assertEquals(salario, empleado.getSalario());
    }
}
