package empleado.exception;

public class FechaIngresoInvalidaException extends EmpleadoInvalidoException {
    public FechaIngresoInvalidaException() {
        super("La fecha de ingreso no puede ser posterior a la fecha actual");
    }
}
