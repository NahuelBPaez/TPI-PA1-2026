package empleado.exception;

public class LegajoInvalidoException extends EmpleadoInvalidoException {
    public LegajoInvalidoException(String legajo) {
        super("El legajo '" + legajo
                + "' no cumple el formato: 3 letras mayúsculas seguidas de 4 números (ej. ABC1234)");
    }
}
