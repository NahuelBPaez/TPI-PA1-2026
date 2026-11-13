package empleado.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PuestoTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("puestosConSalarioMinimo")
    void cadaPuestoConoceSuSalarioMinimo(Puesto puesto, BigDecimal esperado) {
        assertEquals(0, esperado.compareTo(puesto.salarioMinimo()));
    }

    static Stream<Arguments> puestosConSalarioMinimo() {
        return Stream.of(
                Arguments.of(Puesto.ANALISTA, new BigDecimal("500000")),
                Arguments.of(Puesto.SUPERVISOR, new BigDecimal("800000")),
                Arguments.of(Puesto.GERENTE, new BigDecimal("1200000"))
        );
    }
}
