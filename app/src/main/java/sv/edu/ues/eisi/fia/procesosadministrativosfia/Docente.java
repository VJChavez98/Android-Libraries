package sv.edu.ues.eisi.fia.procesosadministrativosfia;

import java.io.Serializable;

public class Docente implements Serializable {
    String coddocente;
    String nomdocente;
    String apellidodocente;

    public Docente() {
    }

    public Docente(String coddocente, String nomdocente, String apellidodocente) {
        this.coddocente = coddocente;
        this.nomdocente = nomdocente;
        this.apellidodocente = apellidodocente;
    }

    public String getCoddocente() {
        return coddocente;
    }

    public void setCoddocente(String coddocente) {
        this.coddocente = coddocente;
    }

    public String getNomdocente() {
        return nomdocente;
    }

    public void setNomdocente(String nomdocente) {
        this.nomdocente = nomdocente;
    }

    public String getApellidodocente() {
        return apellidodocente;
    }

    public void setApellidodocente(String apellidodocente) {
        this.apellidodocente = apellidodocente;
    }
}
