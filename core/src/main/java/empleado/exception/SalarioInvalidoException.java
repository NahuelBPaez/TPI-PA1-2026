package empleado.exception;

public class SalarioInvalidoException extends EmpleadoInvalidoException {
    public SalarioInvalidoException() {
        super("El salario debe ser mayor a cero");
    }
}
