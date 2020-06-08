package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class EncarImpresiones {


    //################## CONSTRUCTORES ###################
    private String idencargado;
    private String nombreencargado;
    private String apellidoencargado;

    public EncarImpresiones() {
    }

    public EncarImpresiones(String idencargado, String nombreencargado, String apellidoencargado) {
        this.idencargado = idencargado;
        this.nombreencargado = nombreencargado;
        this.apellidoencargado = apellidoencargado;
    }


    //################## GETS ###################
    public String getIdencargado() {
        return idencargado;
    }

    public String getNombreencargado() {
        return nombreencargado;
    }

    public String getApellidoencargado() {
        return apellidoencargado;
    }



    //################## SETS ###################
    public void setIdencargado(String idencargado) {
        this.idencargado = idencargado;
    }

    public void setNombreencargado(String nombreencargado) {
        this.nombreencargado = nombreencargado;
    }

    public void setApellidoencargado(String apellidoencargado) {
        this.apellidoencargado = apellidoencargado;
    }
}
