package empleado.modelo;

import empleado.exception.CampoObligatorioException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.SalarioInvalidoException;
import empleado.exception.SalarioBajoPisoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Empleado {
    private final UUID id;
    private final String nombreCompleto;
    private final String legajo;
    private final LocalDate fechaIngreso;
    private final Puesto puesto;
    private final BigDecimal salario;

    private Empleado(
            UUID id,
            String nombreCompleto,
            String legajo,
            LocalDate fechaIngreso,
            Puesto puesto,
            BigDecimal salario
    ) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public static Empleado instance(
            UUID id,
            String nombreCompleto,
            String legajo,
            LocalDate fechaIngreso,
            Puesto puesto,
            BigDecimal salario
    ) {
        requerido(id, "id");
        requeridoTexto(nombreCompleto, "nombreCompleto");
        requeridoTexto(legajo, "legajo");
        requerido(fechaIngreso, "fechaIngreso");
        requerido(puesto, "puesto");
        requerido(salario, "salario");
        if (fechaIngreso.isAfter(LocalDate.now())) {
            throw new FechaIngresoInvalidaException();
        }
        if (!legajo.matches("[A-Z]{3}[0-9]{4}")) {
            throw new LegajoInvalidoException(legajo);
        }
        if (salario.signum() <= 0) {
            throw new SalarioInvalidoException();
        }
        if (salario.compareTo(puesto.salarioMinimo()) < 0) {
            throw new SalarioBajoPisoException(puesto, puesto.salarioMinimo());
        }
        return new Empleado(id, nombreCompleto, legajo, fechaIngreso, puesto, salario);
    }

    private static void requerido(Object valor, String campo) {
        if (valor == null) {
            throw new CampoObligatorioException(campo);
        }
    }

    private static void requeridoTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new CampoObligatorioException(campo);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getLegajo() {
        return legajo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
