package sv.edu.ues.eisi.fia.procesosadministrativosfia;

public class SolImpresion {

    private String idsolicitudimpresion;
    private String iddocente;
    private  String iddocentedirector;
    private Integer cantidadexamenes;
    private Boolean hojasempaque;
    private Boolean estadoaprobacion;

    //Constructores
    public SolImpresion() {
    }

    public SolImpresion(String iddocente, String iddocentedirector, Integer cantidadexamenes, Boolean hojasempaque, Boolean estadoaprobacion, String idsolicitudimpresion) {
        this.iddocente = iddocente;
        this.iddocentedirector = iddocentedirector;
        this.cantidadexamenes = cantidadexamenes;
        this.hojasempaque = hojasempaque;
        this.estadoaprobacion = estadoaprobacion;
        this.idsolicitudimpresion = idsolicitudimpresion;
    }

    public String getIdsolicitudimpresion() {
        return idsolicitudimpresion;
    }

    public String getIddocente() {
        return iddocente;
    }

    public String getIddocentedirector() {
        return iddocentedirector;
    }

    public Integer getCantidadexamenes() {
        return cantidadexamenes;
    }

    public Boolean getHojasempaque() {
        return hojasempaque;
    }

    public Boolean getEstadoaprobacion() {
        return estadoaprobacion;
    }

    //Set

    public void setIdsolicitudimpresion(String idsolicitudimpresion) {
        this.idsolicitudimpresion = idsolicitudimpresion;
    }

    public void setIddocente(String iddocente) {
        this.iddocente = iddocente;
    }

    public void setIddocentedirector(String iddocentedirector) {
        this.iddocentedirector = iddocentedirector;
    }

    public void setCantidadexamenes(Integer cantidadexamenes) {
        this.cantidadexamenes = cantidadexamenes;
    }

    public void setHojasempaque(Boolean hojasempaque) {
        this.hojasempaque = hojasempaque;
    }

    public void setEstadoaprobacion(Boolean estadoaprobacion) {
        this.estadoaprobacion = estadoaprobacion;
    }

}
