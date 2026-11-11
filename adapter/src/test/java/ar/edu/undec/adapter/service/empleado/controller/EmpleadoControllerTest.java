package ar.edu.undec.adapter.service.empleado.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoWebMapper;
import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoDuplicadoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.SalarioBajoPisoException;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoComando;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmpleadoController.class)
@Import(EmpleadoWebMapper.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrearEmpleadoInput crearInput;

    @MockBean
    private BuscarEmpleadoPorLegajoInput buscarInput;

    @MockBean
    private ListarEmpleadosInput listarInput;

    @Test
    void crear_conDatosValidos_responde201ConElEmpleado() throws Exception {
        Empleado empleado = empleadoEjemplo();
        CrearEmpleadoComando comando = new CrearEmpleadoComando(
                "Ana Perez",
                "ABC1234",
                LocalDate.of(2024, 1, 15),
                Puesto.ANALISTA,
                new BigDecimal("600000")
        );
        when(crearInput.crear(any())).thenReturn(empleado);

        mockMvc.perform(post("/empleados")
                        .contentType("application/json")
                        .content(jsonRequest()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.legajo").value("ABC1234"));

        verify(crearInput).crear(comando);
    }

    @Test
    void crear_conLegajoInvalido_responde400() throws Exception {
        when(crearInput.crear(any())).thenThrow(new LegajoInvalidoException("abc1234"));

        mockMvc.perform(post("/empleados")
                        .contentType("application/json")
                        .content(jsonRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void crear_conSalarioBajoElPiso_responde400() throws Exception {
        when(crearInput.crear(any())).thenThrow(
                new SalarioBajoPisoException(Puesto.ANALISTA, new BigDecimal("500000"))
        );

        mockMvc.perform(post("/empleados")
                        .contentType("application/json")
                        .content(jsonRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void crear_conFechaFutura_responde400() throws Exception {
        when(crearInput.crear(any())).thenThrow(new FechaIngresoInvalidaException());

        mockMvc.perform(post("/empleados")
                        .contentType("application/json")
                        .content(jsonRequest()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void crear_conLegajoDuplicado_responde409() throws Exception {
        when(crearInput.crear(any())).thenThrow(new LegajoDuplicadoException("ABC1234"));

        mockMvc.perform(post("/empleados")
                        .contentType("application/json")
                        .content(jsonRequest()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void buscar_conLegajoExistente_responde200ConElEmpleado() throws Exception {
        when(buscarInput.buscar("ABC1234")).thenReturn(empleadoEjemplo());

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.nombreCompleto").value("Ana Perez"));
    }

    @Test
    void buscar_conLegajoInexistente_responde404() throws Exception {
        when(buscarInput.buscar("ABC1234"))
                .thenThrow(new EmpleadoNoEncontradoException("ABC1234"));

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    void listar_conEmpleados_responde200ConArreglo() throws Exception {
        when(listarInput.listar()).thenReturn(List.of(empleadoEjemplo()));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listar_sinEmpleados_responde200ConArregloVacio() throws Exception {
        when(listarInput.listar()).thenReturn(List.of());

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
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

    private String jsonRequest() {
        return """
                {
                  "nombreCompleto": "Ana Perez",
                  "legajo": "ABC1234",
                  "fechaIngreso": "2024-01-15",
                  "puesto": "ANALISTA",
                  "salario": 600000
                }
                """;
    }
}
