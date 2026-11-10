package empleado.exception;

public class LegajoDuplicadoException extends RuntimeException {
    private final String legajo;

    public LegajoDuplicadoException(String legajo) {
        super("El legajo '" + legajo + "' ya existe");
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }
}
