package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class MotivoCambioNota {
    private String codmotivocambionota;
    private String motivo;

    public MotivoCambioNota() {
    }

    public MotivoCambioNota(String codmotivocambionota, String motivo) {
        this.codmotivocambionota = codmotivocambionota;
        this.motivo = motivo;
    }

    public String getCodmotivocambionota() {
        return codmotivocambionota;
    }

    public void setCodmotivocambionota(String codmotivocambionota) {
        this.codmotivocambionota = codmotivocambionota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
