package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class TipoDocenteCiclo {
    private String idtipodocenteciclo;
    private String nomtipodocenteciclo;

    public TipoDocenteCiclo(){

    }


    public TipoDocenteCiclo(String idtipodocenteciclo, String nomtipodocenteciclo) {
        this.idtipodocenteciclo = idtipodocenteciclo;
        this.nomtipodocenteciclo = nomtipodocenteciclo;
    }

    public String getIdtipodocenteciclo() {
        return idtipodocenteciclo;
    }

    public void setIdtipodocenteciclo(String idtipodocenteciclo) {
        this.idtipodocenteciclo = idtipodocenteciclo;
    }

    public String getNomtipodocenteciclo() {
        return nomtipodocenteciclo;
    }

    public void setNomtipodocenteciclo(String nomtipodocenteciclo) {
        this.nomtipodocenteciclo = nomtipodocenteciclo;
    }
}
