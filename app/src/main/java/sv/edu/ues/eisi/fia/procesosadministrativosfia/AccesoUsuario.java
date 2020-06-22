package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class AccesoUsuario {
    private String idOpcion;
    private String username;

    public AccesoUsuario(String idOpcion, String username) {
        this.idOpcion = idOpcion;
        this.username = username;
    }

    public AccesoUsuario() {
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
