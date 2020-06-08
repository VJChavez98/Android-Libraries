package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class CargaAcademica {
    private String idcargaacademica, codciclo, coddocente, codasignatura, idtipodocenteciclo;

    public CargaAcademica(){

    }

    public CargaAcademica(String idcargaacademica, String codciclo, String coddocente, String codasignatura, String idtipodocenteciclo) {
        this.idcargaacademica = idcargaacademica;
        this.codciclo = codciclo;
        this.coddocente = coddocente;
        this.codasignatura = codasignatura;
        this.idtipodocenteciclo = idtipodocenteciclo;
    }

    public String getIdcargaacademica() {
        return idcargaacademica;
    }

    public void setIdcargaacademica(String idcargaacademica) {
        this.idcargaacademica = idcargaacademica;
    }

    public String getCodciclo() {
        return codciclo;
    }

    public void setCodciclo(String codciclo) {
        this.codciclo = codciclo;
    }

    public String getCoddocente() {
        return coddocente;
    }

    public void setCoddocente(String coddocente) {
        this.coddocente = coddocente;
    }

    public String getCodasignatura() {
        return codasignatura;
    }

    public void setCodasignatura(String codasignatura) {
        this.codasignatura = codasignatura;
    }

    public String getIdtipodocenteciclo() {
        return idtipodocenteciclo;
    }

    public void setIdtipodocenteciclo(String idtipodocenteciclo) {
        this.idtipodocenteciclo = idtipodocenteciclo;
    }
}

