package sv.edu.ues.eisi.fia.procesosadministrativosfia;

class Evaluacion {
    private String codAsignatura;
    private String codTipoEval;
    private String codCiclo;
    private String fechaEvaluacion;
    private int numeroEvaluacion;

    public Evaluacion() {
    }

    public Evaluacion(String codAsignatura, String codTipoEval, String codCiclo, String fechaEvaluacion, int numeroEvaluacion) {
        this.codAsignatura = codAsignatura;
        this.codTipoEval = codTipoEval;
        this.codCiclo = codCiclo;
        this.fechaEvaluacion = fechaEvaluacion;
        this.numeroEvaluacion = numeroEvaluacion;
    }


    public String getCodAsignatura() {
        return codAsignatura;
    }

    public String getCodTipoEval() {
        return codTipoEval;
    }

    public String getCodCiclo() {
        return codCiclo;
    }

    public String getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public int getNumeroEvaluacion() {
        return numeroEvaluacion;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public void setCodTipoEval(String codTipoEval) {
        this.codTipoEval = codTipoEval;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    public void setFechaEvaluacion(String fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public void setNumeroEvaluacion(int numeroEvaluacion) {
        this.numeroEvaluacion = numeroEvaluacion;
    }
}
