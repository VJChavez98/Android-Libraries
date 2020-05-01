package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class SolicitudDiferido {
    private String idSolicitud,carnet, codMateria, codEva, GT, GD, GL, tipoEva, fechaEva, horaEva, motivo, otroMotivo;

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public String getCodEva() {
        return codEva;
    }

    public void setCodEva(String codEva) {
        this.codEva = codEva;
    }

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
    public int getTipoEva(String tipoEva){
        switch (tipoEva){
            case "EP":
                return 1;
            case"ED":
                return 2;
            case "EL":
                return 3;
            default: return 0;
        }
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
    public int getMotivo(String tipoEva){
        switch (tipoEva){
            case "Salud":
                return 1;
            case"Trabajo":
                return 2;
            case "Interferencia":
                return 3;
            case "Viaje":
                return 4;
            case "Duelo":
                return 5;
            case "Otro":
                return 6;
            default: return 0;
        }
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
}
