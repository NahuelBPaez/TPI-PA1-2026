package ar.edu.undec.adapter.service.empleado.beanconfig;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoWebMapper;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.output.EmpleadoRepository;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public CrearEmpleadoInput crearEmpleadoInput(EmpleadoRepository repo) {
        return new CrearEmpleadoUseCase(repo);
    }

    @Bean
    public BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput(EmpleadoRepository repo) {
        return new BuscarEmpleadoPorLegajoUseCase(repo);
    }

    @Bean
    public ListarEmpleadosInput listarEmpleadosInput(EmpleadoRepository repo) {
        return new ListarEmpleadosUseCase(repo);
    }

    @Bean
    public EmpleadoWebMapper empleadoWebMapper() {
        return new EmpleadoWebMapper();
    }
}
