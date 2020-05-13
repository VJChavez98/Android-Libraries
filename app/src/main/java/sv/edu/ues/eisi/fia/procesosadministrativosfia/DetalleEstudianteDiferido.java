package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class DetalleEstudianteDiferido {
    private String carnet, idDetalleDifeRep, idDetalleEstudianteDif, fechaInscripcionDiferido;
    DetalleDiferidoRepetido detalle;

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getIdDetalleDifeRep() {
        return idDetalleDifeRep;
    }

    public void setIdDetalleDifeRep(String idDetalleDifeRep) {
        this.idDetalleDifeRep = idDetalleDifeRep;
    }

    public String getIdDetalleEstudianteDif() {
        return idDetalleEstudianteDif;
    }

    public void setIdDetalleEstudianteDif(String idDetalleEstudianteDif) {
        this.idDetalleEstudianteDif = idDetalleEstudianteDif;
    }

    public String getFechaInscripcionDiferido() {
        return fechaInscripcionDiferido;
    }

    public void setFechaInscripcionDiferido(String fechaInscripcionDiferido) {
        this.fechaInscripcionDiferido = fechaInscripcionDiferido;
    }

    public DetalleDiferidoRepetido getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleDiferidoRepetido detalle) {
        this.detalle = detalle;
    }
    public void llenarDetalle(String materia, String tipoEval, int numEval){
        detalle.setIdAsignatura(materia);
        detalle.setNumEval(numEval);
        detalle.setIdTipoEval(tipoEval);
        detalle.setIdTipoDifRep("Repetido");
        detalle.setIdDetalle();
    }
}
