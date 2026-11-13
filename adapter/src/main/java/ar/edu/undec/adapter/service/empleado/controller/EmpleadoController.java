package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoWebMapper;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {
    private final CrearEmpleadoInput crearEmpleadoInput;
    private final BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput;
    private final ListarEmpleadosInput listarEmpleadosInput;
    private final EmpleadoWebMapper empleadoWebMapper;

    public EmpleadoController(
            CrearEmpleadoInput crearEmpleadoInput,
            BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput,
            ListarEmpleadosInput listarEmpleadosInput,
            EmpleadoWebMapper empleadoWebMapper
    ) {
        this.crearEmpleadoInput = crearEmpleadoInput;
        this.buscarEmpleadoPorLegajoInput = buscarEmpleadoPorLegajoInput;
        this.listarEmpleadosInput = listarEmpleadosInput;
        this.empleadoWebMapper = empleadoWebMapper;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse response = empleadoWebMapper.aRespuesta(
                crearEmpleadoInput.crear(empleadoWebMapper.aComando(request))
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<EmpleadoResponse> buscar(@PathVariable("legajo") String legajo) {
        return ResponseEntity.ok(
                empleadoWebMapper.aRespuesta(buscarEmpleadoPorLegajoInput.buscar(legajo))
        );
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listar() {
        List<EmpleadoResponse> respuestas = listarEmpleadosInput.listar().stream()
                .map(empleadoWebMapper::aRespuesta)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuestas);
    }
}
