package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.util.Date;

public class Ciclo {
    private String codciclo;
    private Date fechadesde;
    private Date fechahasta;

    public Ciclo() {
    }

    public Ciclo(String codciclo, Date fechadesde, Date fechahasta) {
        this.codciclo = codciclo;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
    }

    public String getCodciclo() {
        return codciclo;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setCodciclo(String codciclo) {
        this.codciclo = codciclo;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }
}
