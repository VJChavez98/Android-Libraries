package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class Diferido {
    private String idDiferido;
    MotivoDiferido motivo;
    Estudiante estudiante;
    Evaluacion evaluacion;

    public String getIdDiferido() {
        return idDiferido;
    }

    public void setIdDiferido(String idDiferido) {
        this.idDiferido = idDiferido;
    }

    public MotivoDiferido getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoDiferido motivo) {
        this.motivo = motivo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
    public void llenarEvaluacion(String materia, String tipoEval, int numEval, String fechaEval, String ciclo){
        evaluacion.setCodAsignatura(materia);
        evaluacion.setCodTipoEval(tipoEval);
        evaluacion.setNumeroEvaluacion(numEval);
        evaluacion.setFechaEvaluacion(fechaEval);
        evaluacion.setCodCiclo(ciclo);
    }

}
