package empleado.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import empleado.exception.CampoObligatorioException;
import empleado.exception.EmpleadoInvalidoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Test
    void noSeCreaUnEmpleadoSinId() {
        CampoObligatorioException excepcion = assertThrows(
                CampoObligatorioException.class,
                () -> Empleado.instance(
                        null,
                        "Ana Pérez",
                        "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );

        assertEquals("id", excepcion.getCampo());
        assertInstanceOf(EmpleadoInvalidoException.class, excepcion);
    }

    @ParameterizedTest(name = "{0} nulo")
    @MethodSource("camposNulos")
    void noSeCreaUnEmpleadoConCamposNulos(
            String campoEsperado,
            UUID id,
            String nombreCompleto,
            String legajo,
            LocalDate fechaIngreso,
            Puesto puesto,
            BigDecimal salario
    ) {
        CampoObligatorioException excepcion = assertThrows(
                CampoObligatorioException.class,
                () -> Empleado.instance(
                        id,
                        nombreCompleto,
                        legajo,
                        fechaIngreso,
                        puesto,
                        salario
                )
        );

        assertEquals(campoEsperado, excepcion.getCampo());
    }

    static Stream<Arguments> camposNulos() {
        return Stream.of(
                Arguments.of(
                        "nombreCompleto",
                        UUID.randomUUID(),
                        null,
                        "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                ),
                Arguments.of(
                        "legajo",
                        UUID.randomUUID(),
                        "Ana Pérez",
                        null,
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                ),
                Arguments.of(
                        "fechaIngreso",
                        UUID.randomUUID(),
                        "Ana Pérez",
                        "ABC1234",
                        null,
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                ),
                Arguments.of(
                        "puesto",
                        UUID.randomUUID(),
                        "Ana Pérez",
                        "ABC1234",
                        LocalDate.now().minusDays(1),
                        null,
                        new BigDecimal("500000")
                ),
                Arguments.of(
                        "salario",
                        UUID.randomUUID(),
                        "Ana Pérez",
                        "ABC1234",
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        null
                )
        );
    }
}
