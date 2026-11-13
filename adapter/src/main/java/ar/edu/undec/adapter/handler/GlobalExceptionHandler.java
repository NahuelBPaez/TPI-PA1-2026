package ar.edu.undec.adapter.handler;

import empleado.exception.EmpleadoInvalidoException;
import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.LegajoDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmpleadoInvalidoException.class)
    public ResponseEntity<ErrorResponse> manejarEmpleadoInvalido(EmpleadoInvalidoException excepcion) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(excepcion.getMessage()));
    }

    @ExceptionHandler(LegajoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarLegajoDuplicado(LegajoDuplicadoException excepcion) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(excepcion.getMessage()));
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarEmpleadoNoEncontrado(
            EmpleadoNoEncontradoException excepcion
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(excepcion.getMessage()));
    }
}
