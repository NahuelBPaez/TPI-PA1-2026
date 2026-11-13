package empleado.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuscarEmpleadoPorLegajoUseCaseTest {

    @Mock
    private EmpleadoRepository repo;

    private BuscarEmpleadoPorLegajoUseCase casoDeUso;

    @BeforeEach
    void setUp() {
        casoDeUso = new BuscarEmpleadoPorLegajoUseCase(repo);
    }

    @Test
    void buscar_conLegajoExistente_retornaElEmpleado() {
        Empleado empleado = empleadoEjemplo();
        when(repo.buscarPorLegajo("ABC1234")).thenReturn(Optional.of(empleado));

        Empleado resultado = casoDeUso.buscar("ABC1234");

        assertSame(empleado, resultado);
    }

    @Test
    void buscar_conLegajoInexistente_lanzaEmpleadoNoEncontradoException() {
        String legajo = "ABC1234";
        when(repo.buscarPorLegajo(legajo)).thenReturn(Optional.empty());

        EmpleadoNoEncontradoException excepcion = assertThrows(
                EmpleadoNoEncontradoException.class,
                () -> casoDeUso.buscar(legajo)
        );

        assertEquals(legajo, excepcion.getLegajo());
    }

    @Test
    void buscar_conLegajo_consultaAlRepositorioConEseLegajo() {
        when(repo.buscarPorLegajo("ABC1234")).thenReturn(Optional.of(empleadoEjemplo()));

        casoDeUso.buscar("ABC1234");

        verify(repo, times(1)).buscarPorLegajo("ABC1234");
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
