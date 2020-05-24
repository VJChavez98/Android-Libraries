package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.io.Serializable;

public class TipoGrupo {

    private String codtipogrupo;
    private String nombreTipoGrupo;

    public TipoGrupo() {

    }

    public TipoGrupo(String codtipogrupo, String nombreTipoGrupo) {
        this.codtipogrupo = codtipogrupo;
        this.nombreTipoGrupo = nombreTipoGrupo;
    }

    public String getCodtipogrupo() {
        return codtipogrupo;
    }

    public void setCodtipogrupo(String codtipogrupo) {
        this.codtipogrupo = codtipogrupo;
    }

    public String getNombreTipoGrupo() {
        return nombreTipoGrupo;
    }

    public void setNombreTipoGrupo(String nombreTipoGrupo) {
        this.nombreTipoGrupo = nombreTipoGrupo;
    }
}
