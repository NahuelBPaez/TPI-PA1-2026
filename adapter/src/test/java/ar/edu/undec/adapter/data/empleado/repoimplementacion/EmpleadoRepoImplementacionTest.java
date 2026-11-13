package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoDataMapper;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({EmpleadoRepoImplementacion.class, EmpleadoDataMapper.class})
class EmpleadoRepoImplementacionTest {

    @Autowired
    private EmpleadoRepoImplementacion repositorio;

    @Test
    void guardar_conEmpleadoValido_retornaTrueYLuegoSePuedeRecuperar() {
        Empleado empleado = empleado("ABC1234", "Ana Perez", Puesto.ANALISTA, "600000");

        assertTrue(repositorio.guardar(empleado));

        Optional<Empleado> resultado = repositorio.buscarPorLegajo("ABC1234");

        assertTrue(resultado.isPresent());
        assertEquals(empleado.getId(), resultado.get().getId());
        assertEquals(empleado.getNombreCompleto(), resultado.get().getNombreCompleto());
        assertEquals(empleado.getPuesto(), resultado.get().getPuesto());
        assertEquals(empleado.getSalario(), resultado.get().getSalario());
    }

    @Test
    void existePorLegajo_conLegajoGuardado_retornaTrue() {
        repositorio.guardar(empleado("DEF5678", "Luis Gomez", Puesto.SUPERVISOR, "900000"));

        assertTrue(repositorio.existePorLegajo("DEF5678"));
    }

    @Test
    void existePorLegajo_conLegajoNoGuardado_retornaFalse() {
        assertFalse(repositorio.existePorLegajo("GHI9012"));
    }

    @Test
    void buscarPorLegajo_conLegajoInexistente_retornaOptionalVacio() {
        assertTrue(repositorio.buscarPorLegajo("JKL3456").isEmpty());
    }

    @Test
    void listar_conVariosEmpleadosGuardados_retornaTodos() {
        repositorio.guardar(empleado("MNO7890", "Ana Perez", Puesto.ANALISTA, "600000"));
        repositorio.guardar(empleado("PQR1234", "Luis Gomez", Puesto.SUPERVISOR, "900000"));

        List<Empleado> resultado = repositorio.listar();

        assertEquals(2, resultado.size());
    }

    private Empleado empleado(String legajo, String nombre, Puesto puesto, String salario) {
        return Empleado.instance(
                UUID.randomUUID(),
                nombre,
                legajo,
                LocalDate.of(2024, 1, 15),
                puesto,
                new BigDecimal(salario)
        );
    }
}
