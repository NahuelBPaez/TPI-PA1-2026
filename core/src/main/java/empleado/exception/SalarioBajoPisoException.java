package empleado.exception;

import empleado.modelo.Puesto;
import java.math.BigDecimal;

public class SalarioBajoPisoException extends EmpleadoInvalidoException {
    public SalarioBajoPisoException(Puesto puesto, BigDecimal minimo) {
        super("El salario mínimo para " + puesto + " es " + minimo);
    }
}
