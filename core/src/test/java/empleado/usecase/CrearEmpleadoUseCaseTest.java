package empleado.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import empleado.exception.LegajoDuplicadoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.PersistenciaEmpleadoException;
import empleado.input.CrearEmpleadoComando;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoUseCaseTest {

    @Mock
    private EmpleadoRepository repo;

    private CrearEmpleadoUseCase casoDeUso;

    @BeforeEach
    void setUp() {
        casoDeUso = new CrearEmpleadoUseCase(repo);
    }

    @Test
    void crear_conDatosValidos_retornaEmpleadoConLosDatosDelComando() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(false);
        when(repo.guardar(any(Empleado.class))).thenReturn(true);

        Empleado empleado = casoDeUso.crear(comando);

        assertEquals(comando.legajo(), empleado.getLegajo());
        assertEquals(comando.nombreCompleto(), empleado.getNombreCompleto());
        assertEquals(comando.puesto(), empleado.getPuesto());
        assertEquals(comando.salario(), empleado.getSalario());
    }

    @Test
    void crear_conDatosValidos_generaIdNoNulo() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(false);
        when(repo.guardar(any(Empleado.class))).thenReturn(true);

        Empleado empleado = casoDeUso.crear(comando);

        assertNotNull(empleado.getId());
    }

    @Test
    void crear_conDatosValidos_guardaElEmpleadoUnaVez() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(false);
        when(repo.guardar(any(Empleado.class))).thenReturn(true);

        casoDeUso.crear(comando);

        verify(repo, times(1)).guardar(any(Empleado.class));
    }

    @Test
    void crear_conLegajoExistente_lanzaLegajoDuplicadoException() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(true);

        assertThrows(LegajoDuplicadoException.class, () -> casoDeUso.crear(comando));
    }

    @Test
    void crear_conLegajoExistente_nuncaGuarda() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(true);

        assertThrows(LegajoDuplicadoException.class, () -> casoDeUso.crear(comando));
        verify(repo, never()).guardar(any(Empleado.class));
    }

    @Test
    void crear_cuandoGuardarFalla_lanzaPersistenciaEmpleadoException() {
        CrearEmpleadoComando comando = comandoValido();
        when(repo.existePorLegajo(comando.legajo())).thenReturn(false);
        when(repo.guardar(any(Empleado.class))).thenReturn(false);

        assertThrows(PersistenciaEmpleadoException.class, () -> casoDeUso.crear(comando));
    }

    @Test
    void crear_conLegajoConFormatoInvalido_propagaLegajoInvalidoException() {
        CrearEmpleadoComando comando = new CrearEmpleadoComando(
                "Ana Perez",
                "abc1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        assertThrows(LegajoInvalidoException.class, () -> casoDeUso.crear(comando));
    }

    @Test
    void crear_conDatosInvalidos_noInteractuaConElRepositorio() {
        CrearEmpleadoComando comando = new CrearEmpleadoComando(
                "Ana Perez",
                "abc1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );

        assertThrows(LegajoInvalidoException.class, () -> casoDeUso.crear(comando));
        verifyNoInteractions(repo);
    }

    private CrearEmpleadoComando comandoValido() {
        return new CrearEmpleadoComando(
                "Ana Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );
    }
}
