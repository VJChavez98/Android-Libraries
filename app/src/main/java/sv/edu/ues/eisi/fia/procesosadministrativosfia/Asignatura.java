package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class Asignatura {
    private String codasignatura;
    private String nomasignatura;
    private String unidadesval;

    public Asignatura(){
    }

    public Asignatura(String codasignatura, String nomasignatura, String unidadesval) {
        this.codasignatura = codasignatura;
        this.nomasignatura = nomasignatura;
        this.unidadesval = unidadesval;
    }

    public String getCodasignatura() {
        return codasignatura;
    }

    public String getNomasignatura() {
        return nomasignatura;
    }

    public String getUnidadesval() {
        return unidadesval;
    }

    public void setCodasignatura(String codasignatura) {
        this.codasignatura = codasignatura;
    }

    public void setNomasignatura(String nomasignatura) {
        this.nomasignatura = nomasignatura;
    }

    public void setUnidadesval(String unidadesval) {
        this.unidadesval = unidadesval;
    }
}
