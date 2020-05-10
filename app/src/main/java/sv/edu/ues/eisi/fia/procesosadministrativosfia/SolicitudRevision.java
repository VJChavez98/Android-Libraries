package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.sql.Date;

public class SolicitudRevision {
    private Date fechasolicitudrevision;
    private float notaantesrevision;
    private int numerogrupo;
    private String motivorevision;
    private String carnet;
    private String codasignatura;
    private String codciclo;
    private String codtipoeval;
    private int numeroeval;
    private String codtiporevision;

    public SolicitudRevision() {
    }

    public SolicitudRevision(Date fechasolicitudrevision, float notaantesrevision, int numerogrupo, String motivorevision, String carnet, String codasignatura, String codciclo, String codtipoeval, int numeroeval, String codtiporevision) {
        this.fechasolicitudrevision = fechasolicitudrevision;
        this.notaantesrevision = notaantesrevision;
        this.numerogrupo = numerogrupo;
        this.motivorevision = motivorevision;
        this.carnet = carnet;
        this.codasignatura = codasignatura;
        this.codciclo = codciclo;
        this.codtipoeval = codtipoeval;
        this.numeroeval = numeroeval;
        this.codtiporevision = codtiporevision;
    }

    public float getNotaantesrevision() {
        return notaantesrevision;
    }

    public void setNotaantesrevision(float notaantesrevision) {
        this.notaantesrevision = notaantesrevision;
    }

    public int getNumerogrupo() {
        return numerogrupo;
    }

    public void setNumerogrupo(int numerogrupo) {
        this.numerogrupo = numerogrupo;
    }

    public String getMotivorevision() {
        return motivorevision;
    }

    public void setMotivorevision(String motivorevision) {
        this.motivorevision = motivorevision;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCodasignatura() {
        return codasignatura;
    }

    public void setCodasignatura(String codasignatura) {
        this.codasignatura = codasignatura;
    }

    public String getCodciclo() {
        return codciclo;
    }

    public void setCodciclo(String codciclo) {
        this.codciclo = codciclo;
    }

    public String getCodtipoeval() {
        return codtipoeval;
    }

    public void setCodtipoeval(String codtipoeval) {
        this.codtipoeval = codtipoeval;
    }

    public int getNumeroeval() {
        return numeroeval;
    }

    public void setNumeroeval(int numeroeval) {
        this.numeroeval = numeroeval;
    }

    public String getCodtiporevision() {
        return codtiporevision;
    }

    public void setCodtiporevision(String codtiporevision) {
        this.codtiporevision = codtiporevision;
    }

    public Date getFechasolicitudrevision() {
        return fechasolicitudrevision;
    }

    public void setFechasolicitudrevision(Date fechasolicitudrevision) {
        this.fechasolicitudrevision = fechasolicitudrevision;
    }
}
