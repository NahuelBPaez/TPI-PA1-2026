package empleado.exception;

public class EmpleadoInvalidoException extends RuntimeException {
    public EmpleadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
