package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class DocDirector {

    private String iddocentedirector;
    private String idescuela;
    private String nombredirector;
    private String apellidodirector;
    private String correodirector;
    private Integer telefono;


    //###########################CONSTRUCTORES################################
    public DocDirector() {
    }

    public DocDirector(String iddocentedirector, String idescuela, String nombredirector, String apellidodirector, String correodirector, Integer telefono) {
        this.iddocentedirector = iddocentedirector;
        this.idescuela = idescuela;
        this.nombredirector = nombredirector;
        this.apellidodirector = apellidodirector;
        this.correodirector = correodirector;
        this.telefono = telefono;
    }


    //###########################GET################################
    public String getIddocentedirector() {
        return iddocentedirector;
    }

    public String getIdescuela() {
        return idescuela;
    }

    public String getNombredirector() {
        return nombredirector;
    }

    public String getApellidodirector() {
        return apellidodirector;
    }

    public String getCorreodirector() {
        return correodirector;
    }

    public Integer getTelefono() {
        return telefono;
    }


//###########################SET################################
    public void setIddocentedirector(String iddocentedirector) {
        this.iddocentedirector = iddocentedirector;
    }

    public void setIdescuela(String idescuela) {
        this.idescuela = idescuela;
    }

    public void setNombredirector(String nombredirector) {
        this.nombredirector = nombredirector;
    }

    public void setApellidodirector(String apellidodirector) {
        this.apellidodirector = apellidodirector;
    }

    public void setCorreodirector(String correodirector) {
        this.correodirector = correodirector;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
}
