package sv.edu.ues.eisi.fia.procesosadministrativosfia;


import java.util.Date;

public class Ciclo {
    private String codciclo;
    private String fechadesde;
    private String fechahasta;

    public Ciclo() {
    }

    public Ciclo(String codciclo, String fechadesde, String fechahasta) {
        this.codciclo = codciclo;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
    }

    public String getCodciclo() {
        return codciclo;
    }

    public void setCodciclo(String codciclo) {
        this.codciclo = codciclo;
    }

    public String getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(String fechadesde) {
        this.fechadesde = fechadesde;
    }

    public String getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(String fechahasta) {
        this.fechahasta = fechahasta;
    }
}