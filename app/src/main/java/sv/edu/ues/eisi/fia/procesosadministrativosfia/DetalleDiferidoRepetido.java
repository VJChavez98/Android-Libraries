package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class DetalleDiferidoRepetido {
    private String idDetalle, idLocal, idDocente, idTipoDifRep, fechaDesde, fechaHasta, fechaRealizacion, horaRealizacion, idAsignatura, idTipoEval;
    private int numEval;

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }
    public void setIdDetalle(){
        this.idDetalle = idAsignatura+idTipoEval+numEval+idTipoDifRep;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(String idDocente) {
        this.idDocente = idDocente;
    }

    public String getIdTipoDifRep() {
        return idTipoDifRep;
    }

    public void setIdTipoDifRep(String idTipoDifRep) {
        this.idTipoDifRep = idTipoDifRep;
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

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getHoraRealizacion() {
        return horaRealizacion;
    }

    public void setHoraRealizacion(String horaRealizacion) {
        this.horaRealizacion = horaRealizacion;
    }

    public String getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(String idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getIdTipoEval() {
        return idTipoEval;
    }

    public void setIdTipoEval(String idTipoEval) {
        this.idTipoEval = idTipoEval;
    }

    public int getNumEval() {
        return numEval;
    }

    public void setNumEval(int numEval) {
        this.numEval = numEval;
    }
}
