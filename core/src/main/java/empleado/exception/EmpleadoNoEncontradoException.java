package empleado.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {
    private final String legajo;

    public EmpleadoNoEncontradoException(String legajo) {
        super("No se encontró un empleado con legajo '" + legajo + "'");
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }
}
