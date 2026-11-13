package ar.edu.undec.adapter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void flujoCompleto_crearYLuegoBuscar_funcionaDeExtremoAExtremo() throws Exception {
        String request = """
                {
                  "nombreCompleto": "Ana Perez",
                  "legajo": "ABC1234",
                  "fechaIngreso": "2024-01-15",
                  "puesto": "ANALISTA",
                  "salario": 600000
                }
                """;

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.legajo").value("ABC1234"));

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Ana Perez"));
    }
}
