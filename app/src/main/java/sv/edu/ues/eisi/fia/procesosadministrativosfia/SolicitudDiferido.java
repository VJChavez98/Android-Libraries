package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class SolicitudDiferido {
    private String idSolicitud,carnet, codMateria, GT, GD, GL, tipoEva, fechaEva, horaEva, motivo, otroMotivo, estado, rutaJustificante;
    private int numeroEval;
    public String getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    public void setIdSolicitud() {
        this.idSolicitud = carnet+codMateria+tipoEva+numeroEval ;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }

    public int getNumeroEval() { return numeroEval;}

    public void setNumeroEval(int numeroEval) { this.numeroEval = numeroEval;}

    public String getGT() {
        return GT;
    }

    public void setGT(String GT) {
        this.GT = GT;
    }

    public String getGD() {
        return GD;
    }

    public void setGD(String GD) {
        this.GD = GD;
    }

    public String getGL() {
        return GL;
    }

    public void setGL(String GL) {
        this.GL = GL;
    }

    public String getTipoEva() {
        return tipoEva;
    }

    public void setTipoEva(String tipoEva) {
        this.tipoEva = tipoEva;
    }

    public String getFechaEva() {
        return fechaEva;
    }

    public void setFechaEva(String fechaEva) {
        this.fechaEva = fechaEva;
    }

    public String getHoraEva() {
        return horaEva;
    }

    public void setHoraEva(String horaEva) {
        this.horaEva = horaEva;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOtroMotivo() {
        return otroMotivo;
    }

    public void setOtroMotivo(String otroMotivo) {
        this.otroMotivo = otroMotivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutaJustificante() {
        return rutaJustificante;
    }

    public void setRutaJustificante(String rutaJustificante) {
        this.rutaJustificante = rutaJustificante;
    }
}
