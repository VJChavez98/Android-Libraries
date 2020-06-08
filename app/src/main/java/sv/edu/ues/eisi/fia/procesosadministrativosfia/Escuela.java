package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class Escuela {

    private String idescuela;
    private String nombreescuela;
    private String facultad;

    public Escuela() {
    }

    public Escuela(String idescuela, String nombreescuela, String facultad) {
        this.idescuela = idescuela;
        this.nombreescuela = nombreescuela;
        this.facultad = facultad;
    }

    public String getIdescuela() {
        return idescuela;
    }

    public String getNombreescuela() {
        return nombreescuela;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setIdescuela(String idescuela) {
        this.idescuela = idescuela;
    }

    public void setNombreescuela(String nombreescuela) {
        this.nombreescuela = nombreescuela;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}
