package empleado.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ListarEmpleadosUseCaseTest {

    @Mock
    private EmpleadoRepository repo;

    private ListarEmpleadosUseCase casoDeUso;

    @BeforeEach
    void setUp() {
        casoDeUso = new ListarEmpleadosUseCase(repo);
    }

    @Test
    void listar_sinEmpleados_retornaListaVacia() {
        when(repo.listar()).thenReturn(List.of());

        List<Empleado> resultado = casoDeUso.listar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void listar_conEmpleados_retornaTodosLosDelRepositorio() {
        Empleado ana = Empleado.instance(
                UUID.randomUUID(),
                "Ana Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );
        Empleado luis = Empleado.instance(
                UUID.randomUUID(),
                "Luis Gomez",
                "DEF5678",
                LocalDate.of(2024, 1, 15),
                Puesto.SUPERVISOR,
                new BigDecimal("900000")
        );
        when(repo.listar()).thenReturn(List.of(ana, luis));

        List<Empleado> resultado = casoDeUso.listar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(ana));
        assertTrue(resultado.contains(luis));
    }
}
