package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class TipoRevision {
    private String codTipoRev;
    private String nomTipoRev;

    public TipoRevision() {
    }

    public TipoRevision(String codTipoRev, String nomTipoRev) {
        this.codTipoRev = codTipoRev;
        this.nomTipoRev = nomTipoRev;
    }

    public String getCodTipoRev() {
        return codTipoRev;
    }

    public String getNomTipoRev() {
        return nomTipoRev;
    }

    public void setCodTipoRev(String codTipoRev) {
        this.codTipoRev = codTipoRev;
    }

    public void setNomTipoRev(String nomTipoRev) {
        this.nomTipoRev = nomTipoRev;
    }
}
