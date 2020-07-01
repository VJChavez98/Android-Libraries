package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class MotivoImpresion {

    private String idmotivoimpresion;
    private String motivo;

    public MotivoImpresion() {
    }

    public MotivoImpresion(String idmotivoimpresion, String motivo) {
        this.idmotivoimpresion = idmotivoimpresion;
        this.motivo = motivo;
    }

    public String getIdmotivoimpresion() {
        return idmotivoimpresion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setIdmotivoimpresion(String idmotivoimpresion) {
        this.idmotivoimpresion = idmotivoimpresion;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
