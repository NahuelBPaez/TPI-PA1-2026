package empleado.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import empleado.exception.CampoObligatorioException;
import empleado.exception.EmpleadoInvalidoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest(name = "{0} en blanco: [{1}][{2}]")
    @MethodSource("textosEnBlanco")
    void noSeCreaUnEmpleadoConTextosEnBlanco(
            String campoEsperado,
            String nombreCompleto,
            String legajo
    ) {
        CampoObligatorioException excepcion = assertThrows(
                CampoObligatorioException.class,
                () -> Empleado.instance(
                        UUID.randomUUID(),
                        nombreCompleto,
                        legajo,
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );

        assertEquals(campoEsperado, excepcion.getCampo());
    }

    static Stream<Arguments> textosEnBlanco() {
        return Stream.of(
                Arguments.of("nombreCompleto", "", "ABC1234"),
                Arguments.of("nombreCompleto", "   ", "ABC1234"),
                Arguments.of("legajo", "Ana Pérez", ""),
                Arguments.of("legajo", "Ana Pérez", "   ")
        );
    }

    @ParameterizedTest
    @MethodSource("fechasDeIngresoValidas")
    void seAceptaFechaDeIngresoHoyOAnterior(LocalDate fechaIngreso) {
        Empleado empleado = assertDoesNotThrow(
                () -> Empleado.instance(
                        UUID.randomUUID(),
                        "Ana Pérez",
                        "ABC1234",
                        fechaIngreso,
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );

        assertEquals(fechaIngreso, empleado.getFechaIngreso());
    }

    static Stream<LocalDate> fechasDeIngresoValidas() {
        return Stream.of(
                LocalDate.now(),
                LocalDate.now().minusDays(1)
        );
    }

    @Test
    void noSeCreaUnEmpleadoConFechaDeIngresoFutura() {
        FechaIngresoInvalidaException excepcion = assertThrows(
                FechaIngresoInvalidaException.class,
                () -> Empleado.instance(
                        UUID.randomUUID(),
                        "Ana Pérez",
                        "ABC1234",
                        LocalDate.now().plusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );

        assertInstanceOf(EmpleadoInvalidoException.class, excepcion);
    }

    @ParameterizedTest(name = "legajo [{0}]")
    @ValueSource(strings = {
            "abc1234",
            "AB1234",
            "ABCD1234",
            "ABC123",
            "ABC12345",
            "AB1C234",
            "1234ABC",
            " ABC1234"
    })
    void noSeCreaUnEmpleadoConLegajoConFormatoInvalido(String legajo) {
        LegajoInvalidoException excepcion = assertThrows(
                LegajoInvalidoException.class,
                () -> Empleado.instance(
                        UUID.randomUUID(),
                        "Ana Pérez",
                        legajo,
                        LocalDate.now().minusDays(1),
                        Puesto.ANALISTA,
                        new BigDecimal("500000")
                )
        );

        assertInstanceOf(EmpleadoInvalidoException.class, excepcion);
    }
}
