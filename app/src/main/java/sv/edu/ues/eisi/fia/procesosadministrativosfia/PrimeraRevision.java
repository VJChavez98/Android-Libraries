package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class PrimeraRevision {
    private String estadoprimerrevision;
    private float notadespuesprimerarevision;
    private String asistio;
    private String observacionesprimerarev;
    private String coddocente;
    private String carnet;
    private String codasignatura;
    private String codciclo;
    private String codtipoeval;
    private int numeroeval;
    private String codtiporevision;
    private String motivoCambioNota;

    public String getCodtipogrupo() {
        return codtipogrupo;
    }

    public void setCodtipogrupo(String codtipogrupo) {
        this.codtipogrupo = codtipogrupo;
    }

    private String codtipogrupo;

    public PrimeraRevision(String estadoprimerrevision, float notadespuesprimerarevision, String asistio, String observacionesprimerarev, String coddocente, String carnet, String codasignatura, String codciclo, String codtipoeval, int numeroeval, String codtiporevision, String motivoCambioNota, String codtipogrupo) {
        this.estadoprimerrevision = estadoprimerrevision;
        this.notadespuesprimerarevision = notadespuesprimerarevision;
        this.asistio = asistio;
        this.observacionesprimerarev = observacionesprimerarev;
        this.coddocente = coddocente;
        this.carnet = carnet;
        this.codasignatura = codasignatura;
        this.codciclo = codciclo;
        this.codtipoeval = codtipoeval;
        this.numeroeval = numeroeval;
        this.codtiporevision = codtiporevision;
        this.motivoCambioNota = motivoCambioNota;
        this.codtipogrupo = codtipogrupo;
    }

    public PrimeraRevision() {
    }

    public String getEstadoprimerrevision() {
        return estadoprimerrevision;
    }

    public void setEstadoprimerrevision(String estadoprimerrevision) {
        this.estadoprimerrevision = estadoprimerrevision;
    }

    public float getNotadespuesprimerarevision() {
        return notadespuesprimerarevision;
    }

    public void setNotadespuesprimerarevision(float notadespuesprimerarevision) {
        this.notadespuesprimerarevision = notadespuesprimerarevision;
    }

    public String getAsistio() {
        return asistio;
    }

    public void setAsistio(String asistio) {
        this.asistio = asistio;
    }

    public String getObservacionesprimerarev() {
        return observacionesprimerarev;
    }

    public void setObservacionesprimerarev(String observacionesprimerarev) {
        this.observacionesprimerarev = observacionesprimerarev;
    }

    public String getCoddocente() {
        return coddocente;
    }

    public void setCoddocente(String coddocente) {
        this.coddocente = coddocente;
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

    public String getMotivoCambioNota() {
        return motivoCambioNota;
    }

    public void setMotivoCambioNota(String motivoCambioNota) {
        this.motivoCambioNota = motivoCambioNota;
    }
}
