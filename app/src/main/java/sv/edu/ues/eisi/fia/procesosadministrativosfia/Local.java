package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class Local {
    String codlocal;
    String nomlocal;
    String ubicacionlocal;

    public Local() {
    }

    public Local(String codlocal, String nomlocal, String ubicacionlocal) {
        this.codlocal = codlocal;
        this.nomlocal = nomlocal;
        this.ubicacionlocal = ubicacionlocal;
    }

    public String getCodlocal() {
        return codlocal;
    }

    public void setCodlocal(String codlocal) {
        this.codlocal = codlocal;
    }

    public String getNomlocal() {
        return nomlocal;
    }

    public void setNomlocal(String nomlocal) {
        this.nomlocal = nomlocal;
    }

    public String getUbicacionlocal() {
        return ubicacionlocal;
    }

    public void setUbicacionlocal(String ubicacionlocal) {
        this.ubicacionlocal = ubicacionlocal;
    }
}
