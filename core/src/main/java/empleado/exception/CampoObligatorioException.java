package empleado.exception;

public class CampoObligatorioException extends EmpleadoInvalidoException {
    private final String campo;

    public CampoObligatorioException(String campo) {
        super("El campo '" + campo + "' es obligatorio");
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
