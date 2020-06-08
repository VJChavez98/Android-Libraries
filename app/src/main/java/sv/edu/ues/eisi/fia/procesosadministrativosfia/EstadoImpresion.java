package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class EstadoImpresion {

    private String idestadoimpresion;
    private String idsolicitudimpresion;
    private String idencargado;
    private String idmotivoimpresion;
    private Boolean realizado;
    private String observaciones;

    public EstadoImpresion() {
    }

    public EstadoImpresion(String idestadoimpresion, String idsolicitudimpresion, String idencargado, String idmotivoimpresion, Boolean realizado, String observaciones) {
        this.idestadoimpresion = idestadoimpresion;
        this.idsolicitudimpresion = idsolicitudimpresion;
        this.idencargado = idencargado;
        this.idmotivoimpresion = idmotivoimpresion;
        this.realizado = realizado;
        this.observaciones = observaciones;
    }

    public String getIdestadoimpresion() {
        return idestadoimpresion;
    }

    public String getIdsolicitudimpresion() {
        return idsolicitudimpresion;
    }

    public String getIdencargado() {
        return idencargado;
    }

    public String getIdmotivoimpresion() {
        return idmotivoimpresion;
    }

    public Boolean getRealizado() {
        return realizado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdestadoimpresion(String idestadoimpresion) {
        this.idestadoimpresion = idestadoimpresion;
    }

    public void setIdsolicitudimpresion(String idsolicitudimpresion) {
        this.idsolicitudimpresion = idsolicitudimpresion;
    }

    public void setIdencargado(String idencargado) {
        this.idencargado = idencargado;
    }

    public void setIdmotivoimpresion(String idmotivoimpresion) {
        this.idmotivoimpresion = idmotivoimpresion;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
