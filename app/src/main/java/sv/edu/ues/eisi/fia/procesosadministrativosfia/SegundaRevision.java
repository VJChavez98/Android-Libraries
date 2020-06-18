package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class SegundaRevision {
    private String idsegundarevision;
    private String codtipogrupo;
    private String coddocente;
    private String observacionessegundarev;
    private String carnet;
    private String codasignatura;
    private String codciclo;
    private String codtipoeval;
    private int numeroeval;
    private String codtiporevision;
    private String motivoCambioNota;
    private float notafinalsegundarev;

    public SegundaRevision() {
    }

    public SegundaRevision(String idsegundarevision, String codtipogrupo, String coddocente, String observacionessegundarev, String carnet, String codasignatura, String codciclo, String codtipoeval, int numeroeval, String codtiporevision, String motivoCambioNota, float notafinalsegundarev) {
        this.idsegundarevision = idsegundarevision;
        this.codtipogrupo = codtipogrupo;
        this.coddocente = coddocente;
        this.observacionessegundarev = observacionessegundarev;
        this.carnet = carnet;
        this.codasignatura = codasignatura;
        this.codciclo = codciclo;
        this.codtipoeval = codtipoeval;
        this.numeroeval = numeroeval;
        this.codtiporevision = codtiporevision;
        this.motivoCambioNota = motivoCambioNota;
        this.notafinalsegundarev = notafinalsegundarev;
    }

    public String getIdsegundarevision() {
        return idsegundarevision;
    }

    public void setIdsegundarevision(String idsegundarevision) {
        this.idsegundarevision = idsegundarevision;
    }

    public String getCodtipogrupo() {
        return codtipogrupo;
    }

    public void setCodtipogrupo(String codtipogrupo) {
        this.codtipogrupo = codtipogrupo;
    }

    public String getCoddocente() {
        return coddocente;
    }

    public void setCoddocente(String coddocente) {
        this.coddocente = coddocente;
    }

    public String getObservacionessegundarev() {
        return observacionessegundarev;
    }

    public void setObservacionessegundarev(String observacionessegundarev) {
        this.observacionessegundarev = observacionessegundarev;
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

    public float getNotafinalsegundarev() {
        return notafinalsegundarev;
    }

    public void setNotafinalsegundarev(float notafinalsegundarev) {
        this.notafinalsegundarev = notafinalsegundarev;
    }
}
