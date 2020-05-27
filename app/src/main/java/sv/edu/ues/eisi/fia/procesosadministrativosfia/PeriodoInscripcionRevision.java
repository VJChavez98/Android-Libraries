package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class PeriodoInscripcionRevision {
    private String tipoRevision;
    private String codDocente;
    private String codLocal;
    private String codAsignatura;
    private String codCiclo;
    private String codTipoEval;
    private String fechaDesde;
    private String fechaHasta;
    private String fechaRevision;
    private String horaRevision;
    private int numeroEval;

    public PeriodoInscripcionRevision() {
    }

    public PeriodoInscripcionRevision(String tipoRevision, String codDocente, String codLocal, String codAsignatura, String codCiclo, String codTipoEval, String fechaDesde, String fechaHasta, String fechaRevision, String horaRevision, int numeroEval) {
        this.tipoRevision = tipoRevision;
        this.codDocente = codDocente;
        this.codLocal = codLocal;
        this.codAsignatura = codAsignatura;
        this.codCiclo = codCiclo;
        this.codTipoEval = codTipoEval;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaRevision = fechaRevision;
        this.horaRevision = horaRevision;
        this.numeroEval = numeroEval;
    }

    public String getTipoRevision() {
        return tipoRevision;
    }

    public void setTipoRevision(String tipoRevision) {
        this.tipoRevision = tipoRevision;
    }

    public String getCodDocente() {
        return codDocente;
    }

    public void setCodDocente(String codDocente) {
        this.codDocente = codDocente;
    }

    public String getCodLocal() {
        return codLocal;
    }

    public void setCodLocal(String codLocal) {
        this.codLocal = codLocal;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getCodCiclo() {
        return codCiclo;
    }

    public void setCodCiclo(String codCiclo) {
        this.codCiclo = codCiclo;
    }

    public String getCodTipoEval() {
        return codTipoEval;
    }

    public void setCodTipoEval(String codTipoEval) {
        this.codTipoEval = codTipoEval;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getHoraRevision() {
        return horaRevision;
    }

    public void setHoraRevision(String horaRevision) {
        this.horaRevision = horaRevision;
    }

    public int getNumeroEval() {
        return numeroEval;
    }

    public void setNumeroEval(int numeroEval) {
        this.numeroEval = numeroEval;
    }
}
