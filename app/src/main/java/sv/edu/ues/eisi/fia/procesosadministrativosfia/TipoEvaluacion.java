package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class TipoEvaluacion {
    String codTipoEval;
    String nomTipoEval;

    public TipoEvaluacion() {
    }

    public TipoEvaluacion(String codTipoEval, String nomTipoEval) {
        this.codTipoEval = codTipoEval;
        this.nomTipoEval = nomTipoEval;
    }

    public String getCodTipoEval() {
        return codTipoEval;
    }

    public String getNomTipoEval() {
        return nomTipoEval;
    }

    public void setCodTipoEval(String codTipoEval) {
        this.codTipoEval = codTipoEval;
    }

    public void setNomTipoEval(String nomTipoEval) {
        this.nomTipoEval = nomTipoEval;
    }
}
